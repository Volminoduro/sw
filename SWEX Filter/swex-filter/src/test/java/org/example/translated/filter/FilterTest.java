package org.example.translated.filter;

import org.example.translated.rune.Quality;
import org.example.translated.rune.Rune;
import org.example.translated.rune.Set;
import org.example.translated.stat.InnateStat;
import org.example.translated.stat.MainStat;
import org.example.translated.stat.TypeStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterTest {

    Rune runeForSetTest;
    Rune runeForQualityTest;
    Rune runeForStarsTest;
    Rune runeForMainStatsTest;
    Rune runeForInnateStatsTest;
    Filter filter;

    @BeforeEach
    void setUp() {
        runeForSetTest = new Rune();
        runeForSetTest.setSet(Set.Rage);

        runeForQualityTest = new Rune();
        runeForQualityTest.setQuality(Quality.LEGEND);

        runeForStarsTest = new Rune();
        runeForStarsTest.setStars(5);

        runeForMainStatsTest = new Rune();
        MainStat mainStat = new MainStat();
        mainStat.setTypeStat(TypeStat.SPD);
        runeForMainStatsTest.setMainStat(mainStat);

        runeForInnateStatsTest = new Rune();
        InnateStat innateStat = new InnateStat();
        innateStat.setTypeStat(TypeStat.SPD);
        runeForInnateStatsTest.setInnateStat(innateStat);

        filter = new SubStatsProcNumberFilter();
    }

    @Test
    void noFilter(){
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_OneOfSets() {
        filter.getSets().add(Set.Endure);
        filter.getSets().add(Set.Rage);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_IntangibleSet() {
        runeForSetTest.setSet(Set.Intangible);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_NoMatchSet() {
        filter.getSets().add(Set.Blade);
        assertFalse(filter.isEligible(runeForSetTest));
    }
    @Test
    void isEligibleSet_AllSets() {
        Collections.addAll(filter.getSets(), Set.values());
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleQuality_OneOfQualities() {
        filter.getQualities().add(Quality.LEGEND);
        filter.getQualities().add(Quality.ANCIENT_COMMON);
        assertTrue(filter.isEligible(runeForQualityTest));
    }
    @Test
    void isEligibleQuality_NoMatchQuality() {
        filter.getQualities().add(Quality.RARE);
        assertFalse(filter.isEligible(runeForQualityTest));
    }
    @Test
    void isEligibleQuality_AllQualities() {
        Collections.addAll(filter.getQualities(), Quality.values());
        assertTrue(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleStars_OneOfStars() {
        filter.getStars().add(6);
        filter.getStars().add(5);
        assertTrue(filter.isEligible(runeForStarsTest));
    }
    @Test
    void isEligibleStars_NoMatchStar() {
        filter.getStars().add(2);
        assertFalse(filter.isEligible(runeForStarsTest));
    }
    @Test
    void isEligibleStars_AllStars() {
        filter.getStars().addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertTrue(filter.isEligible(runeForStarsTest));
    }

    @Test
    void isEligibleMainStats_OneOfTypeStat() {
        filter.getMainStats().add(TypeStat.ACC);
        filter.getMainStats().add(TypeStat.SPD);
        assertTrue(filter.isEligible(runeForMainStatsTest));
    }
    @Test
    void isEligibleMainStats_NoMatchTypeStat() {
        filter.getMainStats().add(TypeStat.DEF_FLAT);
        assertFalse(filter.isEligible(runeForMainStatsTest));
    }
    @Test
    void isEligibleMainStats_AllTypeStats() {
        Collections.addAll(filter.getMainStats(), TypeStat.values());
        assertTrue(filter.isEligible(runeForMainStatsTest));
    }

    @Test
    void isEligibleInnateStats_OneOfTypeStat() {
        filter.getInnateStats().add(TypeStat.ACC);
        filter.getInnateStats().add(TypeStat.SPD);
        assertTrue(filter.isEligible(runeForInnateStatsTest));
    }

    @Test
    void isEligibleInnateStats_NoMatchTypeStat() {
        filter.getInnateStats().add(TypeStat.DEF_FLAT);
        assertFalse(filter.isEligible(runeForInnateStatsTest));
    }

    @Test
    void isEligibleInnateStats_AllTypeStats() {
        Collections.addAll(filter.getInnateStats(), TypeStat.values());
        assertTrue(filter.isEligible(runeForInnateStatsTest));
    }

}