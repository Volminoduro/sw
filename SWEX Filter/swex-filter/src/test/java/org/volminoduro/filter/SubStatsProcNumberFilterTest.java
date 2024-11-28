package org.volminoduro.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubStatsProcNumberFilterTest {

    Rune rune;
    SubStatsProcNumberFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune(0, null, null, 1, null, 0,
                null, null, List.of(new SubStat(TypeStat.SPD, 1, false, 0)), null);

        filter = new SubStatsProcNumberFilter();
    }

    @Test
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() throws IOException {
        filter.setSubStatsProcNumber(0);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    @Disabled
    void isEligibleSubStats_OneProcOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setSubStatsProcNumber(1);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    @Disabled
    void isEligibleSubStats_NoneProcMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsProcNumber(1);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    @Disabled
    void isEligibleSubStats_NotEnoughProcMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsProcNumber(2);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    @Disabled
    void isEligibleSubStats_MultipleProcMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsProcNumber(2);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }

    @Test
    @Disabled
    void isEligibleSubStats_MoreProcOfMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsProcNumber(1);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }
}