package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.json.MonsterJSON;
import org.example.json.RuneJSON;
import org.example.key.JSONKey;
import org.example.translated.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslatorTest {

    MonsterJSON monsterJSON;
    RuneJSON runeJSON;

    @BeforeEach
    void setUp() {
        JsonNode monsterJSONNode = new ObjectMapper().createObjectNode()
                .put(JSONKey.UNIT_MASTER_ID.value, "23015")
                .put(JSONKey.RUNES.value, "[]");
        this.monsterJSON = new MonsterJSON(monsterJSONNode);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subStatsRuneArrayNode = objectMapper.valueToTree(Arrays.asList(
                new int[]{9, 4, 1, 0},
                new int[]{12, 11, 0, 6},
                new int[]{2, 18, 0, 0},
                new int[]{8, 10, 0, 0}));

        ObjectNode runeJSONNode = new ObjectMapper().createObjectNode()
                .put(JSONKey.RUNE_ID.value, 321)
                .put(JSONKey.SLOT_NO.value, 2)
                .put(JSONKey.SET_ID.value, 1)
                .put(JSONKey.RANK.value, 5)
                .put(JSONKey.UPGRADE_CURR.value, 12);
        runeJSONNode.set(JSONKey.PRI_EFF.value, objectMapper.valueToTree(new int[]{4, 118}));
        runeJSONNode.set(JSONKey.PREFIX_EFF.value, objectMapper.valueToTree(new int[]{0, 0}));
        runeJSONNode.set(JSONKey.SEC_EFF.value, subStatsRuneArrayNode);

        this.runeJSON = new RuneJSON(runeJSONNode);
    }

    @Test
    void translateMonsterJSON() throws IOException {
        Translator.getInstance();
        Monster monster = Translator.translateMonsterJSON(monsterJSON);
        assertEquals("Eirgar", monster.getName());
    }

    @Test
    void translateRuneJSON() throws IOException {
        Rune rune = new Rune();
        rune.setId("321");
        rune.setLocation(Location.SLOT_2);
        rune.setSet(Set.Energy);
        rune.setQuality(Quality.LEGEND);
        rune.setUpgraded(12);

        MainStat mainStat = new MainStat();
        mainStat.setTypeStat(TypeStat.ATK_PERCENT);
        mainStat.setAmount(118);
        rune.setMainStat(mainStat);

        rune.setInnateStat(new InnateStat());

        SubStat subStat1 = new SubStat();
        subStat1.setTypeStat(TypeStat.CRATE);
        subStat1.setAmount(4);
        subStat1.setEnchanted(true);

        SubStat subStat2 = new SubStat();
        subStat2.setTypeStat(TypeStat.ACC);
        subStat2.setAmount(11);
        subStat2.setGrind(6);

        SubStat subStat3 = new SubStat();
        subStat3.setTypeStat(TypeStat.HP_PERCENT);
        subStat3.setAmount(18);

        SubStat subStat4 = new SubStat();
        subStat4.setTypeStat(TypeStat.SPD);
        subStat4.setAmount(10);

        rune.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        Monster monsterPossessing = new Monster();
        monsterPossessing.setName("Eirgar");
        monsterPossessing.setId("23015");
        rune.setPossessedByMonster(monsterPossessing);
        Translator.getInstance();

        assertEquals(rune, Translator.translateRuneJSON(runeJSON, monsterJSON));
    }
}