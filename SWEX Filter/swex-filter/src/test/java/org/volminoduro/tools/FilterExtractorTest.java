package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

class FilterExtractorTest {

    private static final String FILTER_TEST_FILE_PATH = "src/test/resources/tools/filterExtractorTest.json";

    @BeforeAll
    static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterExtractor.getInstance(objectMapper.readTree(new File(FILTER_TEST_FILE_PATH)));
    }

    @Test
    void extractFilter_NoNameException() {

        fail("TODO");
    }

    @Test
    void extractFilter_NoType() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSet() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSet_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithQuality() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithQuality_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithStars() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithStars_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithUpgraded() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithUpgraded_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSlot() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSlot_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithMainStat() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithMainStat_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithInnateStat() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithInnateStat_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSubStat() {
        fail("TODO");
    }

    @Test
    void extractFilter_WithSubStat_All() {
        fail("TODO");
    }

    @Test
    void extractFilter_SubStatsNumberFilter_NoAmount() {
        fail("TODO");
    }

    @Test
    void extractFilter_SubStatsNumberFilter_WithAmount() {
        fail("TODO");
    }

    @Test
    void extractFilter_EfficiencyRelativeSubStatsFilter_NoThreshold() {
        fail("TODO");
    }

    @Test
    void extractFilter_EfficiencyRelativeSubStatsFilter_WithThreshold() {
        fail("TODO");
    }
}