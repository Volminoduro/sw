package org.example.translated.filter;

import org.example.translated.rune.Rune;
import org.example.translated.stat.SubStat;
import org.example.translated.stat.TypeStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class EfficiencyRelativeSubStatsFilterTest {

    Rune rune;
    EfficiencyRelativeSubStatsFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune();
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.SPD);
        subStat.setAmount(1);
        rune.getSubStats().add(subStat);

        filter = new EfficiencyRelativeSubStatsFilter();
    }

    @Test
    void noFilter() {
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void noFilter_0SubStatChosen() {
        filter.setEfficiencyRelativeThreshold(0F);
        assertTrue(filter.isEligible(rune));
    }

    @Test
    void isEligibleSubStats_OneProcOfTypeStat() {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setEfficiencyRelativeThreshold(10F);
        assertTrue(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_NoneProcMatchOfTypeStat() {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setEfficiencyRelativeThreshold(1F);
        assertFalse(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_NotEnoughProcMatchOfTypeStat() {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setEfficiencyRelativeThreshold(2F);
        assertFalse(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_MultipleProcMatchsOfTypeStat() {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setEfficiencyRelativeThreshold(2F);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_MoreProcOfMatchsOfTypeStat() {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setEfficiencyRelativeThreshold(1F);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
        fail();
    }
}