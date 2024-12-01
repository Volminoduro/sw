package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Rune;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Filter {

    String name = "";
    Collection<RuneSet> runeSets = new ArrayList<>();
    Collection<RuneQuality> qualities = new ArrayList<>();
    Collection<Integer> stars = new ArrayList<>();
    Collection<Integer> upgraded = new ArrayList<>();
    Collection<Integer> slot = new ArrayList<>();
    Collection<TypeStat> mainStats = new ArrayList<>();
    Collection<TypeStat> innateStats = new ArrayList<>();
    Collection<TypeStat> subStats = new ArrayList<>();
    boolean selectGrindstoneFromInventory;
    boolean selectGemFromInventory;

    public boolean isEligible(Rune rune) throws IOException {
        return isQualitiesEligible(rune)
                && isSetsEligible(rune)
                && isUpgradedEligible(rune)
                && isStarsEligible(rune)
                && isMainStatsEligible(rune)
                && isInnateStatsEligible(rune)
                && isSubStatsEligible(rune);
    }

    private boolean isUpgradedEligible(Rune rune) {
        return upgraded.isEmpty() || upgraded.contains(rune.upgraded());
    }

    private boolean isStarsEligible(Rune rune) {
        return stars.isEmpty() || stars.contains(rune.stars());
    }

    private boolean isSetsEligible(Rune rune) {
        return runeSets.isEmpty() || runeSets.contains(rune.runeSet()) || rune.runeSet() == RuneSet.Intangible;
    }

    private boolean isQualitiesEligible(Rune rune) {
        return qualities.isEmpty() || qualities.contains(rune.runeQuality());
    }

    private boolean isMainStatsEligible(Rune rune) {
        return mainStats.isEmpty() || mainStats.contains(rune.mainStat().typeStat());
    }

    private boolean isInnateStatsEligible(Rune rune) {
        return innateStats.isEmpty() || innateStats.contains(rune.innateStat().typeStat());
    }

    abstract boolean isSubStatsEligible(Rune rune) throws IOException;
}
