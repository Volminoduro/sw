package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.enums.translated.Quality;
import org.volminoduro.enums.translated.Set;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;

import java.io.IOException;
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

    public boolean isEligible(Rune rune) throws IOException {
        return isQualitiesEligible(rune)
                && isSetsEligible(rune)
                && isStarsEligible(rune)
                && isMainStatsEligible(rune)
                && isInnateStatsEligible(rune)
                && isSubStatsEligible(rune);
    }

    private boolean isStarsEligible(Rune rune) {
        return stars.isEmpty() || stars.contains(rune.stars());
    }

    private boolean isSetsEligible(Rune rune) {
        return sets.isEmpty() || sets.contains(rune.set()) || rune.set() == Set.Intangible;
    }

    private boolean isQualitiesEligible(Rune rune) {
        return qualities.isEmpty() || qualities.contains(rune.quality());
    }

    private boolean isMainStatsEligible(Rune rune) {
        return mainStats.isEmpty() || mainStats.contains(rune.mainStat().typeStat());
    }

    private boolean isInnateStatsEligible(Rune rune) {
        return innateStats.isEmpty() || innateStats.contains(rune.innateStat().typeStat());
    }

    abstract boolean isSubStatsEligible(Rune rune) throws IOException;
}
