package org.volminoduro.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
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
    Rune runeForUpgradedTest;
    Rune runeForMainStatsTest;
    Rune runeForInnateStatsTest;
    Filter filter;
    Rune runeForSlotTest;

    @BeforeEach
    void setUp() {
        runeForSetTest = new Rune(0, null, null, 0, RuneSet.Rage, 0,
                null, null, null, null, Collections.EMPTY_LIST);

        runeForQualityTest = new Rune(0, null, RuneQuality.LEGEND, 0, null, 0,
                null, null, null, null, Collections.EMPTY_LIST);

        runeForStarsTest = new Rune(0, null, null, 5, null, 0,
                null, null, null, null, Collections.EMPTY_LIST);

        runeForUpgradedTest = new Rune(0, null, null, 5, null, 6,
                null, null, null, null, Collections.EMPTY_LIST);

        runeForMainStatsTest = new Rune(0, null, null, 0, null, 0,
                new MainStat(TypeStat.SPD, 0), null, null, null, Collections.EMPTY_LIST);

        runeForInnateStatsTest = new Rune(0, null, null, 0, null, 0,
                null, new InnateStat(TypeStat.SPD, 0), null, null, Collections.EMPTY_LIST);

        runeForSlotTest = new Rune(0, RuneSlot.SLOT_2, null, 0, null, 0,
                null, new InnateStat(TypeStat.SPD, 0), null, null, Collections.EMPTY_LIST);

        filter = new SubStatsNumberFilter();
    }

    @Test
    void noFilter() throws IOException {
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_OneOfSets() throws IOException {
        filter.getRuneSets().add(RuneSet.Endure);
        filter.getRuneSets().add(RuneSet.Rage);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_IntangibleSet() throws IOException {
        runeForSetTest = new Rune(0, null, null, 0, RuneSet.Intangible, 0,
                null, null, null, null, Collections.EMPTY_LIST);
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_NoMatchSet() throws IOException {
        filter.getRuneSets().add(RuneSet.Blade);
        assertFalse(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleSet_AllSets() throws IOException {
        Collections.addAll(filter.getRuneSets(), RuneSet.values());
        assertTrue(filter.isEligible(runeForSetTest));
    }

    @Test
    void isEligibleQuality_OneOfQualities() throws IOException {
        filter.getQualities().add(RuneQuality.LEGEND);
        filter.getQualities().add(RuneQuality.ANCIENT_COMMON);
        assertTrue(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleQuality_NoMatchQuality() throws IOException {
        filter.getQualities().add(RuneQuality.RARE);
        assertFalse(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleQuality_AllQualities() throws IOException {
        Collections.addAll(filter.getQualities(), RuneQuality.values());
        assertTrue(filter.isEligible(runeForQualityTest));
    }

    @Test
    void isEligibleSlot_OneOfSlots() throws IOException {
        filter.getSlot().add(RuneSlot.SLOT_2);
        filter.getSlot().add(RuneSlot.SLOT_3);
        assertTrue(filter.isEligible(runeForSlotTest));
    }

    @Test
    void isEligibleSlot_NoMatchSlot() throws IOException {
        filter.getSlot().add(RuneSlot.SLOT_1);
        assertFalse(filter.isEligible(runeForSlotTest));
    }

    @Test
    void isEligibleSlot_AllSlots() throws IOException {
        Collections.addAll(filter.getSlot(), RuneSlot.values());
        assertTrue(filter.isEligible(runeForSlotTest));
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
    void isEligibleUpgraded_OneOfUpgraded() throws IOException {
        filter.getUpgraded().add(6);
        filter.getUpgraded().add(9);
        assertTrue(filter.isEligible(runeForUpgradedTest));
    }

    @Test
    void isEligibleUpgraded_NoMatchUpgraded() throws IOException {
        filter.getUpgraded().add(3);
        assertFalse(filter.isEligible(runeForUpgradedTest));
    }

    @Test
    void isEligibleUpgraded_AllUpgraded() throws IOException {
        filter.getUpgraded().addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        assertTrue(filter.isEligible(runeForUpgradedTest));
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