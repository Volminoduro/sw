package org.volminoduro.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.Quality;
import org.volminoduro.enums.translated.Set;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;

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
        runeForSetTest = new Rune(0, null, null, 0, Set.Rage, 0,
                null, null, null, null);

        runeForQualityTest = new Rune(0, null, Quality.LEGEND, 0, null, 0,
                null, null, null, null);

        runeForStarsTest = new Rune(0, null, null, 5, null, 0,
                null, null, null, null);

        runeForMainStatsTest = new Rune(0, null, null, 0, null, 0,
                new MainStat(TypeStat.SPD, 0), null, null, null);

        runeForInnateStatsTest = new Rune(0, null, null, 0, null, 0,
                null, new InnateStat(TypeStat.SPD, 0), null, null);

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
        runeForSetTest = new Rune(0, null, null, 0, Set.Intangible, 0,
                null, null, null, null);
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