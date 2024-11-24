package org.example.translated.filter;

import org.example.translated.rune.Quality;
import org.example.translated.rune.Rune;
import org.example.translated.rune.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void isEligibleSet() {
        Rune rune = new Rune();
        rune.setSet(Set.Rage);
        Filter filterTrueSet = new SubPropertyProcNumberFilter();
        // No filter
        assertTrue(filterTrueSet.isEligible(rune));
        // One of sets
        filterTrueSet.getSets().add(Set.Endure);
        filterTrueSet.getSets().add(Set.Rage);
        assertTrue(filterTrueSet.isEligible(rune));

        // Intangible
        rune = new Rune();
        rune.setSet(Set.Intangible);
        assertTrue(filterTrueSet.isEligible(rune));

        // False
        Filter filterFalseSet = new SubPropertyProcNumberFilter();
        filterFalseSet.getSets().add(Set.Blade);
        rune = new Rune();
        rune.setSet(Set.Fight);
        assertFalse(filterFalseSet.isEligible(rune));

        // ALL
        Filter filterAllSets = new SubPropertyProcNumberFilter();
        Collections.addAll(filterAllSets.getSets(), Set.values());
        assertTrue(filterAllSets.isEligible(rune));
    }

    @Test
    void isEligibleQuality() {
        // One of sets
        Rune rune = new Rune();
        rune.setQuality(Quality.LEGEND);
        Filter filterTrueSet = new SubPropertyProcNumberFilter();
        // No filter
        assertTrue(filterTrueSet.isEligible(rune));
        // One of qualities
        filterTrueSet.getQualities().add(Quality.LEGEND);
        filterTrueSet.getQualities().add(Quality.ANCIENT_COMMON);
        assertTrue(filterTrueSet.isEligible(rune));

        // False
        Filter filterFalseSet = new SubPropertyProcNumberFilter();
        filterTrueSet.getQualities().add(Quality.LEGEND);
        rune = new Rune();
        rune.setQuality(Quality.RARE);
        assertFalse(filterFalseSet.isEligible(rune));

        // ALL
        Filter filterAllSets = new SubPropertyProcNumberFilter();
        Collections.addAll(filterAllSets.getQualities(), Quality.values());
        assertTrue(filterAllSets.isEligible(rune));
    }
}