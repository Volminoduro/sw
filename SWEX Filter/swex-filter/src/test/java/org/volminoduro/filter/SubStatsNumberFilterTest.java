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

class SubStatsNumberFilterTest {

    Rune rune;
    SubStatsNumberFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune(0, null, null, 1, null, 0,
                null, null, List.of(new SubStat(TypeStat.SPD, 1, false, 0)), null);

        filter = new SubStatsNumberFilter();
    }

    @Test
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() throws IOException {
        filter.setSubStatsPresenceNumber(0);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_OneOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setSubStatsPresenceNumber(1);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NoneMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsPresenceNumber(1);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NotEnoughMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsPresenceNumber(2);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MultipleMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsPresenceNumber(2);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MoreMatchsOfTypeStat() throws IOException {
        rune = new Rune(0, null, null, 0, null, 0,
                null, null,
                List.of(new SubStat(TypeStat.SPD, 1, false, 0),
                        new SubStat(TypeStat.CRATE, 0, false, 0)),
                null);

        filter.setSubStatsPresenceNumber(1);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }
}