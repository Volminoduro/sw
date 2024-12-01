package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.records.translated.stat.SubStatValue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SWEXMapperTest {

    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";
    MonsterJSON monsterJSON;
    RuneJSON runeJSON;

    @BeforeAll
    static void setUpOnce() throws IOException {
        SWEXMapper.initiateInstance(new ObjectMapper().readTree(new File(MAPPING_FILE_PATH)));
    }

    @BeforeEach
    void setUp() {
        JsonNode monsterJSONNode = new ObjectMapper().createObjectNode()
                .put(SWEXFileJSONKey.UNIT_MASTER_ID.value, "23015")
                .put(SWEXFileJSONKey.RUNES.value, "[]");
        this.monsterJSON = Builder.buildMonsterJSONFromJsonNode(monsterJSONNode);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subStatsRuneArrayNode = objectMapper.valueToTree(Arrays.asList(
                new int[]{9, 4, 1, 0},
                new int[]{12, 11, 0, 6},
                new int[]{2, 18, 0, 0},
                new int[]{8, 10, 0, 0}));

        ObjectNode runeJSONNode = new ObjectMapper().createObjectNode()
                .put(SWEXFileJSONKey.RUNE_ID.value, 321)
                .put(SWEXFileJSONKey.SLOT_NO.value, 2)
                .put(SWEXFileJSONKey.STARS.value, 5)
                .put(SWEXFileJSONKey.SET_ID.value, 1)
                .put(SWEXFileJSONKey.RANK.value, 5)
                .put(SWEXFileJSONKey.UPGRADE_CURR.value, 12);
        runeJSONNode.set(SWEXFileJSONKey.PRI_EFF.value, objectMapper.valueToTree(new int[]{4, 118}));
        runeJSONNode.set(SWEXFileJSONKey.PREFIX_EFF.value, objectMapper.valueToTree(new int[]{0, 0}));
        runeJSONNode.set(SWEXFileJSONKey.SEC_EFF.value, subStatsRuneArrayNode);

        this.runeJSON = Builder.buildRuneJSONFromJsonNode(runeJSONNode);
    }

    @Test
    void getMonsterName() {
        assertEquals("Eirgar", SWEXMapper.getMonsterName(monsterJSON));
    }

    @Test
    void translateRuneJSON() {
        SubStat subStat1 = new SubStat(TypeStat.CRATE, 4, true, 0);
        SubStat subStat2 = new SubStat(TypeStat.ACC, 11, false, 6);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 18);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 10);

        Rune expected = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                5,
                RuneSet.Energy,
                12,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);
        assertEquals(expected, SWEXMapper.translateRuneJSON(runeJSON,
                Builder.buildMonsterFromMonsterJSON(monsterJSON)));
    }

    @Test
    void calculateTotalRelativeEfficiency_MaxEfficiencyForOneSubStat() {
        assertEquals(100,
                SWEXMapper.calculateTotalRelativeEfficiency(List.of(new SubStat(TypeStat.SPD, 30, false, 0)), 6));
        // Considering enchanting case
        assertEquals(20, SWEXMapper.calculateTotalRelativeEfficiency(List.of(new SubStat(TypeStat.SPD, 6, true, 0)),
                6));
        // Ignore grinding case
        assertEquals(100,
                SWEXMapper.calculateTotalRelativeEfficiency(List.of(new SubStat(TypeStat.SPD, 30, false, 5)), 6));
    }

    @Test
    void calculateTotalRelativeEfficiency_With4SubStats() {
        assertEquals(100, SWEXMapper.calculateTotalRelativeEfficiency(List.of(new SubStat(TypeStat.SPD, 30, false, 0),
                        new SubStat(TypeStat.ATK_FLAT, 20, false, 0),
                        new SubStat(TypeStat.ATK_PERCENT, 8, false, 0),
                        new SubStat(TypeStat.CRATE, 6, false, 0)),
                6));
    }

    @Test
    @Disabled
    void calculateSubStatsRoll_NoTotalRollsNoUpgradedRune() {
        SubStat subStat1 = Builder.buildMinimalSubStat(TypeStat.CRATE, 6);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 8);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 8);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 6);

        Rune rune = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                6,
                RuneSet.Energy,
                0,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);

        assertEquals(0, SWEXMapper.calculateSubStatsTotalRolls(rune, List.of(TypeStat.values())));
    }

    @Test
    @Disabled
    void calculateSubStatsRoll_NoTotalRollsInDesiredTypeStat() {
        SubStat subStat1 = Builder.buildMinimalSubStat(TypeStat.CRATE, 6);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 8);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 8);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 30);

        Rune rune = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                6,
                RuneSet.Energy,
                12,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);

        assertEquals(0, SWEXMapper.calculateSubStatsTotalRolls(rune, List.of(TypeStat.DEF_FLAT)));
    }

    @Test
    @Disabled
    void calculateSubStatsTotalRolls_MaxRollsInDesiredTypeStat() {
        SubStat subStat1 = Builder.buildMinimalSubStat(TypeStat.CRATE, 6);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 12);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 8);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 15);

        Rune rune = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                6,
                RuneSet.Energy,
                9,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);

        assertEquals(3, SWEXMapper.calculateSubStatsTotalRolls(rune, List.of(TypeStat.SPD, TypeStat.ACC)));
    }

    @Test
    @Disabled
    void calculateSubStatsTotalRolls_4RollsValueOf3InDesiredTypeStat() {
        SubStat subStat1 = Builder.buildMinimalSubStat(TypeStat.CRATE, 6);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 8);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 8);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 20);

        Rune rune = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                6,
                RuneSet.Energy,
                12,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);

        assertEquals(4, SWEXMapper.calculateSubStatsTotalRolls(rune, List.of(TypeStat.values())));
    }

    @Test
    void getSubStatValue() {
        SubStatValue expected = new SubStatValue(TypeStat.SPD, 6, 30);

        assertEquals(expected, SWEXMapper.getSubStatValue(TypeStat.SPD, 6));
    }
}