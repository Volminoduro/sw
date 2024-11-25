package org.example.translated.filter;

import org.example.translated.rune.Quality;
import org.example.translated.rune.Rune;
import org.example.translated.rune.Set;
import org.example.translated.stat.InnateStat;
import org.example.translated.stat.MainStat;
import org.example.translated.stat.TypeStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_OneOfSets() throws IOException {
        filter.getSets().add(Set.Endure);
        filter.getSets().add(Set.Rage);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_IntangibleSet() throws IOException {
        runeForSetTest.setSet(Set.Intangible);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_NoMatchSet() throws IOException {
        filter.getSets().add(Set.Blade);
        assertFalse(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_AllSets() throws IOException {
        Collections.addAll(filter.getSets(), Set.values());
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleQuality_OneOfQualities() throws IOException {
        filter.getQualities().add(Quality.LEGEND);
        filter.getQualities().add(Quality.ANCIENT_COMMON);
        assertTrue(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleQuality_NoMatchQuality() throws IOException {
        filter.getQualities().add(Quality.RARE);
        assertFalse(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleQuality_AllQualities() throws IOException {
        Collections.addAll(filter.getQualities(), Quality.values());
        assertTrue(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleStars_OneOfStars() throws IOException {
        filter.getStars().add(6);
        filter.getStars().add(5);
        assertTrue(filter.isEligible(runeForStarsTest));
    }

    @Test
    void isEligibleStars_NoMatchStar() throws IOException {
        filter.getStars().add(2);
        assertFalse(filter.isEligible(runeForStarsTest));
    }

    @Test
    void isEligibleStars_AllStars() throws IOException {
        filter.getStars().addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertTrue(filter.isEligible(runeForStarsTest));
    }

    @Test
    void isEligibleMainStats_OneOfTypeStat() throws IOException {
        filter.getMainStats().add(TypeStat.ACC);
        filter.getMainStats().add(TypeStat.SPD);
        assertTrue(filter.isEligible(runeForMainStatsTest));
    }

    @Test
    void isEligibleMainStats_NoMatchTypeStat() throws IOException {
        filter.getMainStats().add(TypeStat.DEF_FLAT);
        assertFalse(filter.isEligible(runeForMainStatsTest));
    }

    @Test
    void isEligibleMainStats_AllTypeStats() throws IOException {
        Collections.addAll(filter.getMainStats(), TypeStat.values());
        assertTrue(filter.isEligible(runeForMainStatsTest));
    }

    @Test
    void isEligibleInnateStats_OneOfTypeStat() throws IOException {
        filter.getInnateStats().add(TypeStat.ACC);
        filter.getInnateStats().add(TypeStat.SPD);
        assertTrue(filter.isEligible(runeForInnateStatsTest));
    }

    @Test
    void isEligibleInnateStats_NoMatchTypeStat() throws IOException {
        filter.getInnateStats().add(TypeStat.DEF_FLAT);
        assertFalse(filter.isEligible(runeForInnateStatsTest));
    }

    @Test
    void isEligibleInnateStats_AllTypeStats() throws IOException {
        Collections.addAll(filter.getInnateStats(), TypeStat.values());
        assertTrue(filter.isEligible(runeForInnateStatsTest));
    }

}