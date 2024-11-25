package org.example.translated.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.translated.rune.Quality;
import org.example.translated.rune.Rune;
import org.example.translated.rune.Set;
import org.example.translated.stat.TypeStat;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Filter {

    Collection<Set> sets = new ArrayList<>();
    Collection<Quality> qualities = new ArrayList<>();
    Collection<Integer> stars = new ArrayList<>();
    Collection<Integer> slot = new ArrayList<>();
    Collection<TypeStat> mainStats = new ArrayList<>();
    Collection<TypeStat> innateStats = new ArrayList<>();
    Collection<TypeStat> subStats = new ArrayList<>();
    boolean selectGrindstoneFromInventory;
    boolean selectGemFromInventory;

    public boolean isEligible(Rune rune) {
        return isQualitiesEligible(rune)
                && isSetsEligible(rune)
                && isStarsEligible(rune)
                && isMainStatsEligible(rune)
                && isInnateStatsEligible(rune)
                && isSubStatsEligible(rune);
    }

    private boolean isStarsEligible(Rune rune) {
        return stars.isEmpty() || stars.contains(rune.getStars());
    }

    private boolean isSetsEligible(Rune rune) {
        return sets.isEmpty() || sets.contains(rune.getSet()) || rune.getSet() == Set.Intangible;
    }

    private boolean isQualitiesEligible(Rune rune) {
        return qualities.isEmpty() || qualities.contains(rune.getQuality());
    }

    private boolean isMainStatsEligible(Rune rune) {
        return mainStats.isEmpty() || mainStats.contains(rune.getMainStat().getTypeStat());
    }

    private boolean isInnateStatsEligible(Rune rune) {
        return innateStats.isEmpty() || innateStats.contains(rune.getInnateStat().getTypeStat());
    }

    abstract boolean isSubStatsEligible(Rune rune);
}
