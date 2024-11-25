package org.example.translated.filter;

import org.example.translated.rune.Rune;
import org.example.translated.stat.SubStat;
import org.example.translated.stat.TypeStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SubStatsProcNumberFilterTest {

    Rune rune;
    SubStatsProcNumberFilter filter;

    @BeforeEach
    void setUp() {
        rune = new Rune();
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.SPD);
        subStat.setAmount(1);
        rune.getSubStats().add(subStat);

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
    void isEligibleSubStats_OneProcOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.ACC);
        filter.getSubStats().add(TypeStat.SPD);
        filter.setSubStatsProcNumber(1);
        assertTrue(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_NoneProcMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.CDMG);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsProcNumber(1);
        assertFalse(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_NotEnoughProcMatchOfTypeStat() throws IOException {
        filter.getSubStats().add(TypeStat.SPD);
        filter.getSubStats().add(TypeStat.DEF_FLAT);
        filter.setSubStatsProcNumber(2);
        assertFalse(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_MultipleProcMatchsOfTypeStat() throws IOException {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setSubStatsProcNumber(2);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
        fail();
    }

    @Test
    void isEligibleSubStats_MoreProcOfMatchsOfTypeStat() throws IOException {
        SubStat subStat = new SubStat();
        subStat.setTypeStat(TypeStat.CRATE);
        rune.getSubStats().add(subStat);

        filter.setSubStatsProcNumber(1);
        Collections.addAll(filter.getSubStats(), TypeStat.values());
        assertTrue(filter.isEligible(rune));
        fail();
    }
}