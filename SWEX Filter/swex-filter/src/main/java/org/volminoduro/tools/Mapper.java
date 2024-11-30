package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.MappingKey;
import org.volminoduro.enums.translated.Location;
import org.volminoduro.enums.translated.Quality;
import org.volminoduro.enums.translated.Set;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.json.SubStatJSON;
import org.volminoduro.records.json.SubStatValueJSON;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.records.translated.stat.SubStatValue;

import java.util.*;

public final class Mapper {

    public static final String UNKNOWN_MONSTER = "Unknown Monster";
    // TODO Save keys already used (performance issues)
    private static final Collection<SubStatValue> mappedSubStatsValue = new ArrayList<>();
    private static JsonNode instancedMappingJsonNode;
    private static Mapper instance;
    // TODO Save keys already used (performance issues incoming)

    private Mapper() {
    }

    public static void initiateInstance(JsonNode mappingJsonNode) {
        if (instance == null) instance = new Mapper();
        instancedMappingJsonNode = mappingJsonNode;
    }

    public static String getMonsterName(MonsterJSON monsterJSON) {
        String monsterName;
        try {
            monsterName = instancedMappingJsonNode.get(MappingKey.MONSTER_LIST.value)
                    .get(MappingKey.MONSTER_NAMES.value)
                    .get(String.valueOf(monsterJSON.id())).asText();
        } catch (NullPointerException unawakenedMonster) {
            try {
                String family = String.valueOf(monsterJSON.id()).substring(0, 3);
                family = instancedMappingJsonNode.get(MappingKey.MONSTER_LIST.value)
                        .get(MappingKey.MONSTER_NAMES.value)
                        .get(family).asText();
                String attribute = String.valueOf(monsterJSON.id()).substring(String.valueOf(monsterJSON.id()).length() - 1);
                attribute = instancedMappingJsonNode.get(MappingKey.MONSTER_LIST.value)
                        .get(MappingKey.MONSTER_ATTRIBUTES.value)
                        .get(attribute).asText();
                String secondAwakening = Character.toString(String.valueOf(monsterJSON.id()).charAt(3)).equals("3") ? " 2A" : "";

                monsterName = family + " " + attribute + secondAwakening;
            } catch (NullPointerException unfoundMonster) {
                monsterName = UNKNOWN_MONSTER;
            }
        }
        return monsterName;
    }

    public static Rune translateRuneJSON(RuneJSON runeJSON, Monster monster) {

        MainStat mainStat = new MainStat(TypeStat.valueOfJsonMappingKey(runeJSON.mainStatJSON().key()),
                runeJSON.mainStatJSON().value());

        InnateStat innateStat = null;
        if (runeJSON.innateStatJSON().key() != 0 && runeJSON.innateStatJSON().value() != 0) {
            innateStat = new InnateStat(TypeStat.valueOfJsonMappingKey(runeJSON.innateStatJSON().key()),
                    runeJSON.innateStatJSON().value());
        }

        Collection<SubStat> subStats = new ArrayList<>();
        SubStat subStat;
        for (SubStatJSON subStatJSON : runeJSON.subStatsJSON()) {
            subStat = new SubStat(TypeStat.valueOfJsonMappingKey(subStatJSON.typeStat()),
                    subStatJSON.amount(), subStatJSON.enchant() == 1, subStatJSON.grind());
            subStats.add(subStat);
        }

        return new Rune(runeJSON.id(), Location.valueOfJsonMappingKey(runeJSON.slot_no()), Quality.valueOfJsonMappingKey(runeJSON.rank()), runeJSON.stars(),
                Set.valueOfJsonMappingKey(runeJSON.set_id()), runeJSON.upgrade_curr(), mainStat, innateStat, subStats, monster);
    }


    public static SubStatValue getSubStatValue(TypeStat typeStat, Integer grade) {
        for (SubStatValue subStatValue : mappedSubStatsValue) {
            if (subStatValue.typeStat() == typeStat && Objects.equals(subStatValue.grade(), grade)) {
                return subStatValue;
            }
        }
        SubStatValue subStatValue = Builder.buildSubStatValueFromSubStatValueJSON(getSubStatValueJSON(typeStat, grade));
        mappedSubStatsValue.add(subStatValue);
        return subStatValue;
    }

    private static SubStatValueJSON getSubStatValueJSON(TypeStat typeStat, Integer grade) {
        JsonNode substatValuesList = instancedMappingJsonNode.get(MappingKey.RUNE_LIST.value).get(MappingKey.RUNE_SUBSTAT.value);

        JsonNode selectedSubStatValue = substatValuesList.get(String.valueOf(typeStat.jsonMappingKey));
        JsonNode maxSubStatValueNode = selectedSubStatValue.get(MappingKey.RUNE_SUBSTAT_MAX.value);
        JsonNode selectedMaxSubStatValue = maxSubStatValueNode.get(grade.toString());
        SubStatValueJSON subStatValueJSON = new SubStatValueJSON();
        subStatValueJSON.setTypeStat(typeStat.jsonMappingKey);
        subStatValueJSON.setGrade(grade);
        subStatValueJSON.setAmountMax(selectedMaxSubStatValue.asInt());
        return subStatValueJSON;
    }

    public static double calculateTotalRelativeEfficiency(Collection<SubStat> subStats, Integer grade) {
        double totalRatio = 0;
        for (SubStat subStat : subStats) {
            totalRatio += (double) subStat.amount() / getSubStatValue(subStat.typeStat(), grade).max() * 100;
        }
        // It's chosen for ancient to go above 100%
        totalRatio /= (80 + (subStats.size() * 20));
        totalRatio *= 100;
        return totalRatio;
    }

    public static double calculateSubStatsTotalRolls(Rune rune, Collection<TypeStat> typeStatRollssDesired) {
        Map<TypeStat, Integer> typeStatsWithRolls = new HashMap<>();

        for (SubStat subStat : rune.subStats()) {
            SubStatValue subStatValue = getSubStatValue(subStat.typeStat(), rune.stars());
            int rollsAmountLeft = subStat.amount() - subStatValue.max() / 5;
            int rollsSuspected = 0;
            if (rollsAmountLeft != 0) {
                rollsSuspected = (int) Math.ceil((float) rollsAmountLeft / (subStatValue.max() / 5));
            }

            typeStatsWithRolls.put(subStat.typeStat(), rollsSuspected);
        }

        // TODO : Fixing too low value rolls
        double maxRollsPossible = Math.floor((double) rune.upgraded() / 3);

        // TODO Ancient runes handling
        return typeStatsWithRolls.entrySet().stream().filter(typeStatIntegerEntry -> typeStatRollssDesired.contains(typeStatIntegerEntry.getKey())).mapToInt(Map.Entry::getValue).sum();
    }
}
