package org.volminoduro.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubStatsRollsNumberFilterTest {

    Rune rune;
    SubStatsRollsNumberFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune(0, null, null, 1, null, 0,
                null, null, List.of(new SubStat(TypeStat.SPD, 1, false, 0)), null);

        filter = new SubStatsRollsNumberFilter();
    }

    @Test
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() throws IOException {
        filter.setSubStatsRollsNumber(0);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_OneRollOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setSubStatsRollsNumber(1);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NoneRollMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsRollsNumber(1);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NotEnoughRollMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsRollsNumber(2);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MultipleRollMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsRollsNumber(2);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MoreRollOfMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsRollsNumber(1);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }
}