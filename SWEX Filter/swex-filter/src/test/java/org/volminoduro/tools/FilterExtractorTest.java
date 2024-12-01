package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.filter.EfficiencyRelativeSubStatsFilter;
import org.volminoduro.filter.SubStatsNumberFilter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterExtractorTest {

    private static final String FILTER_TEST_FILE_PATH = "src/test/resources/tools/filterExtractorTest.json";

    @BeforeAll
    static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterExtractor.getInstance(objectMapper.readTree(new File(FILTER_TEST_FILE_PATH)));
    }

    @Test
    void extractFilter_NoNameIsNotIncluded() {
        fail("TODO");
    }

    @Test
    void extractFilter_NoTypeIsNotIncluded() {
        assertTrue(
                FilterExtractor.extractAllFilters().stream().noneMatch(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_NoTypeIsNotIncluded")));
    }

    @Test
    void extractFilter_WithSet() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSet");
        expected.setRuneSets(List.of(RuneSet.Energy));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSet")).findFirst().get());
    }

    @Test
    void extractFilter_WithSet_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSet_All");
        expected.setRuneSets(List.of(RuneSet.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSet_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithQuality() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithQuality");
        expected.setQualities(List.of(RuneQuality.LEGEND));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithQuality")).findFirst().get());
    }

    @Test
    void extractFilter_WithQuality_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithQuality_All");
        expected.setQualities(List.of(RuneQuality.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithQuality_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithStars() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithStars");
        expected.setStars(List.of(1));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithStars")).findFirst().get());
    }

    @Test
    void extractFilter_WithStars_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithStars_All");
        expected.setStars(List.of(1, 2, 3, 4, 5, 6));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithStars_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithUpgraded() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithUpgraded");
        expected.setUpgraded(List.of(1));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithUpgraded")).findFirst().get());
    }

    @Test
    void extractFilter_WithUpgraded_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithUpgraded_All");
        expected.setUpgraded(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithUpgraded_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithSlot() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSlot");
        expected.setSlot(List.of(RuneSlot.SLOT_5));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSlot")).findFirst().get());
    }

    @Test
    void extractFilter_WithSlot_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSlot_All");
        expected.setSlot(List.of(RuneSlot.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSlot_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithMainStat() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithMainStat");
        expected.setMainStats(List.of(TypeStat.ATK_PERCENT, TypeStat.ATK_FLAT));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithMainStat")).findFirst().get());
    }

    @Test
    void extractFilter_WithMainStat_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithMainStat_All");
        expected.setMainStats(List.of(TypeStat.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithMainStat_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithInnateStat() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithInnateStat");
        expected.setInnateStats(List.of(TypeStat.DEF_FLAT, TypeStat.SPD));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithInnateStat")).findFirst().get());
    }

    @Test
    void extractFilter_WithInnateStat_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithInnateStat_All");
        expected.setInnateStats(List.of(TypeStat.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithInnateStat_All")).findFirst().get());
    }

    @Test
    void extractFilter_WithSubStat() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSubStat");
        expected.setSubStats(List.of(TypeStat.ACC));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSubStat")).findFirst().get());
    }

    @Test
    void extractFilter_WithSubStat_All() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_WithSubStat_All");
        expected.setSubStats(List.of(TypeStat.values()));
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_WithSubStat_All")).findFirst().get());
    }

    @Test
    void extractFilter_SubStatsNumberFilter_NoAmount() {
        SubStatsNumberFilter expected = new SubStatsNumberFilter();
        expected.setName("extractFilter_SubStatsNumberFilter_NoAmount");
        expected.setSubStatsPresenceNumber(0);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_SubStatsNumberFilter_NoAmount")).findFirst().get());
    }

    @Test
    void extractFilter_SubStatsNumberFilter_WithAmount() {
        SubStatsNumberFilter expected = new SubStatsNumberFilter();
        expected.setName("extractFilter_SubStatsNumberFilter_WithAmount");
        expected.setSubStatsPresenceNumber(3);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_SubStatsNumberFilter_WithAmount")).findFirst().get());
    }

    @Test
    void extractFilter_EfficiencyRelativeSubStatsFilter_NoThreshold() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_EfficiencyRelativeSubStatsFilter_NoThreshold");
        expected.setEfficiencyRelativeThreshold(0);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_EfficiencyRelativeSubStatsFilter_NoThreshold")).findFirst().get());
    }

    @Test
    void extractFilter_EfficiencyRelativeSubStatsFilter_WithThreshold() {
        EfficiencyRelativeSubStatsFilter expected = new EfficiencyRelativeSubStatsFilter();
        expected.setName("extractFilter_EfficiencyRelativeSubStatsFilter_WithThreshold");
        expected.setEfficiencyRelativeThreshold(80);
        assertEquals(expected,
                FilterExtractor.extractAllFilters().stream().filter(filter1 -> filter1.getName().equalsIgnoreCase(
                        "extractFilter_EfficiencyRelativeSubStatsFilter_WithThreshold")).findFirst().get());
    }
}