package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json.MonsterJSON;
import org.example.json.RuneJSON;
import org.example.json.SubStatJSON;
import org.example.key.MappingKey;
import org.example.translated.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public final class Translator {

    private static Translator instance;

    // TODO les key déjà mappés à save
    private static HashMap<MappingKey, String> mappedKeys;
    private static JsonNode mappingJSON;

    private Translator() throws IOException {
        mappingJSON = new ObjectMapper().readTree(new File("src/main/resources/mapping.json"));
    }

    public static Translator getInstance() throws IOException {
        if (instance == null) instance = new Translator();
        return instance;
    }

    public static Monster translateMonsterJSON(MonsterJSON monsterJSON) {
        Monster monster = new Monster();
        monster.setId(monsterJSON.getId());
        monster.setName(mappingJSON.get(MappingKey.MONSTER_LIST.value)
                .get(MappingKey.MONSTER_NAMES.value)
                .get(monsterJSON.getId()).asText());

        return monster;
    }

    public static Rune translateRuneJSON(RuneJSON runeJSON) {
        Rune rune = new Rune();
        rune.setId(runeJSON.getId());
        switch (runeJSON.getSlot_no()) {
            case "1" -> rune.setLocation(Location.SLOT_1);
            case "2" -> rune.setLocation(Location.SLOT_2);
            case "3" -> rune.setLocation(Location.SLOT_3);
            case "4" -> rune.setLocation(Location.SLOT_4);
            case "5" -> rune.setLocation(Location.SLOT_5);
            case "6" -> rune.setLocation(Location.SLOT_6);
        }

        switch (runeJSON.getRank()) {
            case "1" -> rune.setQuality(Quality.COMMON);
            case "2" -> rune.setQuality(Quality.MAGIC);
            case "3" -> rune.setQuality(Quality.RARE);
            case "4" -> rune.setQuality(Quality.HERO);
            case "5" -> rune.setQuality(Quality.LEGEND);
            case "11" -> rune.setQuality(Quality.ANCIENT_COMMON);
            case "12" -> rune.setQuality(Quality.ANCIENT_MAGIC);
            case "13" -> rune.setQuality(Quality.ANCIENT_RARE);
            case "14" -> rune.setQuality(Quality.ANCIENT_HERO);
            case "15" -> rune.setQuality(Quality.ANCIENT_LEGEND);
        }

        switch (runeJSON.getSet_id()) {
            case "1" -> rune.setSet(Set.Energy);
            case "2" -> rune.setSet(Set.Guard);
            case "3" -> rune.setSet(Set.Swift);
            case "4" -> rune.setSet(Set.Blade);
            case "5" -> rune.setSet(Set.Rage);
            case "6" -> rune.setSet(Set.Focus);
            case "7" -> rune.setSet(Set.Endure);
            case "8" -> rune.setSet(Set.Fatal);
            case "10" -> rune.setSet(Set.Despair);
            case "11" -> rune.setSet(Set.Vampire);
            case "13" -> rune.setSet(Set.Violent);
            case "14" -> rune.setSet(Set.Nemesis);
            case "15" -> rune.setSet(Set.Will);
            case "16" -> rune.setSet(Set.Shield);
            case "17" -> rune.setSet(Set.Revenge);
            case "18" -> rune.setSet(Set.Destroy);
            case "19" -> rune.setSet(Set.Fight);
            case "20" -> rune.setSet(Set.Determination);
            case "21" -> rune.setSet(Set.Enhance);
            case "22" -> rune.setSet(Set.Accuracy);
            case "23" -> rune.setSet(Set.Tolerance);
            case "24" -> rune.setSet(Set.Seal);
            case "25" -> rune.setSet(Set.Intangible);
            case "99" -> rune.setSet(Set.Immemorial);
        }

        rune.setUpgraded(Integer.parseInt(runeJSON.getUpgrade_curr()));

        MainStat mainStat = new MainStat();
        mainStat.setTypeStat(getTypeStatFromTypeStatInteger(runeJSON.getMainStatJSON().keySet().stream().findFirst().get()));
        mainStat.setAmount(runeJSON.getMainStatJSON().values().stream().findFirst().get());
        rune.setMainStat(mainStat);

        InnateStat innateStat = new InnateStat();
        innateStat.setTypeStat(getTypeStatFromTypeStatInteger(runeJSON.getInnateStatJSON().keySet().stream().findFirst().get()));
        innateStat.setAmount(runeJSON.getInnateStatJSON().values().stream().findFirst().get());
        rune.setInnateStat(innateStat);

        Collection<SubStat> subStats = new ArrayList<>();
        SubStat subStat;
        for (SubStatJSON subStatJSON : runeJSON.getSubStatsJSON()) {
            subStat = new SubStat();
            subStat.setTypeStat(getTypeStatFromTypeStatInteger(subStatJSON.getTypeStat()));

            subStat.setAmount(subStatJSON.getAmount());

            subStat.setGrind(subStatJSON.getGrind());

            subStat.setEnchanted(subStatJSON.getEnchant() == 1);
            subStats.add(subStat);
        }

        rune.setSubStats(subStats);

        return rune;
    }

    private static TypeStat getTypeStatFromTypeStatInteger(Integer typeStatInteger) {
        return switch (typeStatInteger) {
            case 1 -> TypeStat.HP_FLAT;
            case 2 -> TypeStat.HP_PERCENT;
            case 3 -> TypeStat.ATK_FLAT;
            case 4 -> TypeStat.ATK_PERCENT;
            case 5 -> TypeStat.DEF_FLAT;
            case 6 -> TypeStat.DEF_PERCENT;
            case 8 -> TypeStat.SPD;
            case 9 -> TypeStat.CRATE;
            case 10 -> TypeStat.CDMG;
            case 11 -> TypeStat.RES;
            case 12 -> TypeStat.ACC;
            default -> null;
        };
    }

}
