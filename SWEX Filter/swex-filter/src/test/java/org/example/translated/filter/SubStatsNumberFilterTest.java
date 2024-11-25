package org.example.translated.filter;

import org.example.translated.rune.Rune;
import org.example.translated.stat.SubStat;
import org.example.translated.stat.TypeStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubStatsNumberFilterTest {

    Rune rune;
    SubStatsNumberFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune();
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.SPD);
        subStat.setAmount(1);
        rune.getSubStats().add(subStat);

        filter = new SubStatsNumberFilter();
    }

    @Test
    void noFilter() {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() {
        filter.setSubStatsPresenceNumber(0);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_OneOfTypeStat() {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setSubStatsPresenceNumber(1);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NoneMatchOfTypeStat() {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsPresenceNumber(1);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_NotEnoughMatchOfTypeStat() {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsPresenceNumber(2);
        assertFalse(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MultipleMatchsOfTypeStat() {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setSubStatsPresenceNumber(2);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_MoreMatchsOfTypeStat() {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setSubStatsPresenceNumber(1);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
    }
}