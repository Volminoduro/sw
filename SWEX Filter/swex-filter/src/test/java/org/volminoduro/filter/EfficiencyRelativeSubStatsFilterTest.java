package org.volminoduro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.tools.Mapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EfficiencyRelativeSubStatsFilterTest {

    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";
    Rune rune;
    EfficiencyRelativeSubStatsFilter filter;

    @BeforeAll
    static void setUpOnce() throws IOException {
        Mapper.initiateInstance(new ObjectMapper().readTree(new File(MAPPING_FILE_PATH)));
    }

    @BeforeEach
    void setUp() {
        rune = new Rune(0, null, null, 6, null, 0,
                null, null, List.of(new SubStat(TypeStat.SPD, 6, false, 0)), null);

        filter = new EfficiencyRelativeSubStatsFilter();
    }

    @Test
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() throws IOException {
        filter.setEfficiencyRelativeThreshold(0);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NoneOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setEfficiencyRelativeThreshold(1);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NotEnoughEfficiency() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.setEfficiencyRelativeThreshold(100);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NotEnoughEfficiencyMultiplesTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setEfficiencyRelativeThreshold(30);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_EnoughEfficiencyOfOneTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setEfficiencyRelativeThreshold(20);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_EnoughEfficiencyMultiplesTypeStat() throws IOException {
        rune = new Rune(0, null, null, 6, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 18, false, 0),
                        new SubStat(TypeStat.CRATE, 12, false, 0)),
                null);

        filter.setEfficiencyRelativeThreshold(80);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }

}