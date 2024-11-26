package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.key.MappingKey;
import org.example.records.json.MonsterJSON;
import org.example.records.json.RuneJSON;
import org.example.records.json.SubStatJSON;
import org.example.records.translated.Rune;
import org.example.records.translated.stat.InnateStat;
import org.example.records.translated.stat.MainStat;
import org.example.records.translated.stat.SubStat;

import java.util.ArrayList;
import java.util.Collection;

public final class Mapper {

    public static final String UNKNOWN_MONSTER = "Unknown Monster";
    private static JsonNode instancedMappingJsonNode;
    private static Mapper instance;

    // TODO Save keys already used (performance issues incoming)
    private Mapper() {
    }

    public static void getInstance(JsonNode mappingJsonNode) {
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

    public static Rune translateRuneJSON(RuneJSON runeJSON) {
        Rune rune = new Rune();
        rune.setId(runeJSON.id());

        rune.setLocation(Translator.getLocationFromLocationId(runeJSON.slot_no()));

        rune.setQuality(Translator.getQualityFromQualityId(runeJSON.rank()));

        rune.setSet(Translator.getSetFromSetId(runeJSON.set_id()));

        rune.setUpgraded(runeJSON.upgrade_curr());

        MainStat mainStat = new MainStat(Translator.getTypeStatFromTypeStatInteger(runeJSON.mainStatJSON().keySet().stream().findFirst().get()),
                runeJSON.mainStatJSON().values().stream().findFirst().get());
        rune.setMainStat(mainStat);

        // TODO : Try/catch to cancel innate initiation
        InnateStat innateStat = new InnateStat(Translator.getTypeStatFromTypeStatInteger(runeJSON.innateStatJSON().keySet().stream().findFirst().get()),
                runeJSON.innateStatJSON().values().stream().findFirst().get());
        rune.setInnateStat(innateStat);

        Collection<SubStat> subStats = new ArrayList<>();
        SubStat subStat;
        for (SubStatJSON subStatJSON : runeJSON.subStatsJSON()) {
            subStat = new SubStat(Translator.getTypeStatFromTypeStatInteger(subStatJSON.typeStat()),
                    subStatJSON.amount(), subStatJSON.enchant() == 1, subStatJSON.grind());
            subStats.add(subStat);
        }

        rune.setSubStats(subStats);

        return rune;
    }


    public static Rune translateRuneJSON(RuneJSON runeJSON, MonsterJSON monsterJSON) {
        Rune rune = translateRuneJSON(runeJSON);
        rune.setPossessedByMonster(Builder.buildMonsterRecordFromMonsterJSON(getMonsterName(monsterJSON), monsterJSON));
        return rune;
    }


}
