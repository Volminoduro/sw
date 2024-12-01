package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.key.FilterFileJSONKey;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.filter.EfficiencyRelativeSubStatsFilter;
import org.volminoduro.filter.Filter;
import org.volminoduro.filter.SubStatsNumberFilter;
import org.volminoduro.records.Pair;
import org.volminoduro.records.json.FilterJSON;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.json.SubStatJSON;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.tools.exceptions.FilterNoNameException;
import org.volminoduro.tools.exceptions.FilterNoTypeException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuilderTest {

    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";

    @BeforeAll
    static void setUpOnce() throws IOException {
        SWEXMapper.initiateInstance(new ObjectMapper().readTree(new File(MAPPING_FILE_PATH)));
    }

    @Test
    void buildRuneJSONFromJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subStatsRuneArrayNode = objectMapper.valueToTree(List.of(
                new int[]{9, 4, 1, 0},
                new int[]{12, 11, 0, 6},
                new int[]{2, 18, 0, 0},
                new int[]{8, 10, 0, 0}));

        ObjectNode runeJSONNode = new ObjectMapper().createObjectNode()
                .put(SWEXFileJSONKey.RUNE_ID.value, 321)
                .put(SWEXFileJSONKey.SLOT_NO.value, 2)
                .put(SWEXFileJSONKey.STARS.value, 6)
                .put(SWEXFileJSONKey.SET_ID.value, 1)
                .put(SWEXFileJSONKey.RANK.value, 5)
                .put(SWEXFileJSONKey.UPGRADE_CURR.value, 12);
        runeJSONNode.set(SWEXFileJSONKey.PRI_EFF.value, objectMapper.valueToTree(new int[]{4, 118}));
        runeJSONNode.set(SWEXFileJSONKey.PREFIX_EFF.value, objectMapper.valueToTree(new int[]{0, 0}));
        runeJSONNode.set(SWEXFileJSONKey.SEC_EFF.value, subStatsRuneArrayNode);

        RuneJSON expected = new RuneJSON(321,
                2, 6, 5, 1, 12, new Pair<>(4, 118),
                new Pair<>(0, 0),
                Arrays.asList(new SubStatJSON(9, 4, 0, 1),
                        new SubStatJSON(12, 11, 6, 0),
                        new SubStatJSON(2, 18, 0, 0),
                        new SubStatJSON(8, 10, 0, 0)));
        assertEquals(expected, Builder.buildRuneJSONFromJsonNode(runeJSONNode));
    }

    @Test
    void buildMonsterJSONFromJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode monsterJSONNode = objectMapper.createObjectNode()
                .put(SWEXFileJSONKey.UNIT_MASTER_ID.value, 123);
        monsterJSONNode.set(SWEXFileJSONKey.RUNES.value, objectMapper.valueToTree(Collections.emptyList()));

        MonsterJSON expected = new MonsterJSON(123, Collections.emptyList());

        assertEquals(expected, Builder.buildMonsterJSONFromJsonNode(monsterJSONNode));
    }

    @Test
    void buildMonsterFromMonsterJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode monsterJSONNode = objectMapper.createObjectNode()
                .put(SWEXFileJSONKey.UNIT_MASTER_ID.value, 23015);
        monsterJSONNode.set(SWEXFileJSONKey.RUNES.value, objectMapper.valueToTree(Collections.emptyList()));

        Monster expected = new Monster(23015, "Eirgar");

        assertEquals(expected,
                Builder.buildMonsterFromMonsterJSON(Builder.buildMonsterJSONFromJsonNode(monsterJSONNode)));
    }


    @Test
    void builderFilterJSONFromJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode filterJsonNode = objectMapper.createObjectNode()
                .put(FilterFileJSONKey.NAME.value, "filter 1");
        filterJsonNode.set(FilterFileJSONKey.TYPE.value,
                objectMapper.createObjectNode().put(FilterFileJSONKey.TYPE_MODE.value, "efficiency").put(FilterFileJSONKey.TYPE_VALUE.value, 80));
        filterJsonNode.set(FilterFileJSONKey.SETS.value,
                objectMapper.valueToTree(Arrays.asList("Energy", "Guard")));
        filterJsonNode.set(FilterFileJSONKey.QUALITIES.value,
                objectMapper.valueToTree(Arrays.asList("Legend", "ALL")));
        filterJsonNode.set(FilterFileJSONKey.UPGRADED.value,
                objectMapper.valueToTree(List.of("12")));
        filterJsonNode.set(FilterFileJSONKey.STARS.value,
                objectMapper.valueToTree(List.of("1")));
        filterJsonNode.set(FilterFileJSONKey.SLOTS.value,
                objectMapper.valueToTree(List.of("3")));
        filterJsonNode.set(FilterFileJSONKey.MAIN_STAT.value,
                objectMapper.valueToTree(List.of("HP +")));
        filterJsonNode.set(FilterFileJSONKey.INNATE_STAT.value,
                objectMapper.valueToTree(List.of("SPD")));
        filterJsonNode.set(FilterFileJSONKey.SUB_STAT.value,
                objectMapper.valueToTree(Arrays.asList("ACC", "DEF %")));

        FilterJSON expected = new FilterJSON("filter 1", new Pair<>("efficiency", 80), List.of("Energy", "Guard"),
                List.of("Legend", "ALL"), List.of("1"), List.of("12"), List.of("3"), List.of("HP +"), List.of("SPD"),
                List.of("ACC",
                        "DEF %"));
        assertEquals(expected, Builder.buildFilterJSONFromJsonNode(filterJsonNode));
    }

    @Test
    void builderEfficiencyRelativeSubStatsFilterFromFilterJSON() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("efficiency", 80), List.of("Energy", "Guard"),
                List.of("Legend", "ALL"), List.of("1"), List.of("12"), List.of("3"), List.of("HP +"), List.of("SPD"),
                List.of("ACC",
                        "DEF %"));

        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("filter 1");
        expected.setEfficiencyRelativeThreshold(80);
        expected.setRuneSets(List.of(RuneSet.Energy, RuneSet.Guard));
        expected.setQualities(List.of(RuneQuality.values()));
        expected.setStars(List.of(1));
        expected.setUpgraded(List.of(12));
        expected.setSlot(List.of(RuneSlot.SLOT_3));
        expected.setMainStats(List.of(TypeStat.HP_FLAT));
        expected.setInnateStats(List.of(TypeStat.SPD));
        expected.setSubStats(List.of(TypeStat.ACC, TypeStat.DEF_PERCENT));

        assertEquals(EfficiencyRelativeSubStatsFilter.class, Builder.buildFilterFromFilterJSON(filterJSON).getClass());
        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderSubStatsNumberFilterFromFilterJSON() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 3), List.of("Energy", "Guard"),
                List.of("Legend", "ALL"), List.of("1"), List.of("12"), List.of("3"), List.of("HP +"), List.of("SPD"),
                List.of("ACC",
                        "DEF %"));

        SubStatsNumberFilter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setSubStatsPresenceNumber(3);
        expected.setRuneSets(List.of(RuneSet.Energy, RuneSet.Guard));
        expected.setQualities(List.of(RuneQuality.values()));
        expected.setStars(List.of(1));
        expected.setUpgraded(List.of(12));
        expected.setSlot(List.of(RuneSlot.SLOT_3));
        expected.setMainStats(List.of(TypeStat.HP_FLAT));
        expected.setInnateStats(List.of(TypeStat.SPD));
        expected.setSubStats(List.of(TypeStat.ACC, TypeStat.DEF_PERCENT));

        assertEquals(SubStatsNumberFilter.class, Builder.buildFilterFromFilterJSON(filterJSON).getClass());
        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilter_NoNameException() {
        assertThrows(FilterNoNameException.class,
                () -> Builder.buildFilterFromFilterJSON(new FilterJSON("", null, null,
                        null, null, null, null, null, null, null)));
        assertThrows(FilterNoNameException.class,
                () -> Builder.buildFilterFromFilterJSON(new FilterJSON(null, null, null,
                        null, null, null, null, null, null, null)));
        assertThrows(FilterNoNameException.class,
                () -> Builder.buildFilterFromFilterJSON(new FilterJSON("   ", null, null,
                        null, null, null, null, null, null, null)));
    }

    @Test
    void builderFilter_NoTypeException() {
        assertThrows(FilterNoTypeException.class,
                () -> Builder.buildFilterFromFilterJSON(new FilterJSON("no existing type", new Pair<>("no existing " +
                        "type", 0), null,
                        null, null, null, null, null, null, null)));
        assertThrows(FilterNoTypeException.class,
                () -> Builder.buildFilterFromFilterJSON(new FilterJSON("no type input", null, null,
                        null, null, null, null, null, null, null)));
    }

    @Test
    void builderFilterFromFilterJSONWithALLSetsAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), List.of("ALL"),
                null, null, null, null, null, null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setRuneSets(List.of(RuneSet.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLQualitiesAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                List.of("ALL"), null, null, null, null, null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setQualities(List.of(RuneQuality.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLStarsAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, List.of("ALL"), null, null, null, null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setStars(List.of(1, 2, 3, 4, 5, 6));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLUpgradedAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, null, List.of("ALL"), null, null, null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setUpgraded(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLSlotAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, null, null, List.of("ALL"), null, null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setSlot(List.of(RuneSlot.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLMainStatAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, null, null, null, List.of("ALL"), null, null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setMainStats(List.of(TypeStat.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLInnateStatAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, null, null, null, null, List.of("ALL"), null);

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setInnateStats(List.of(TypeStat.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

    @Test
    void builderFilterFromFilterJSONWithALLSubStatAttribute() {
        FilterJSON filterJSON = new FilterJSON("filter 1", new Pair<>("substats", 0), null,
                null, null, null, null, null, null, List.of("ALL"));

        Filter expected = new SubStatsNumberFilter();
        expected.setName("filter 1");
        expected.setSubStats(List.of(TypeStat.values()));

        assertEquals(expected, Builder.buildFilterFromFilterJSON(filterJSON));
    }

}