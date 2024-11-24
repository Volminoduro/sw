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
    Collection<TypeStat> mainProperties = new ArrayList<>();
    Collection<Integer> slot = new ArrayList<>();
    Collection<TypeStat> subStats = new ArrayList<>();
    boolean selectGrindstoneFromInventory;
    boolean selectGemFromInventory;

    public boolean isEligible(Rune rune) {
        if (!isQualitiesNotEligible(rune))
            return false;
        if (!isSetsNotEligible(rune))
            return false;
        return !isStarsNotEligible(rune);
    }

    private boolean isStarsNotEligible(Rune rune) {
        return !stars.isEmpty() && !stars.contains(rune.getStars());
    }

    private boolean isSetsNotEligible(Rune rune) {
        return !sets.isEmpty() && !sets.contains(rune.getSet()) && rune.getSet() != Set.Intangible;
    }

    private boolean isQualitiesNotEligible(Rune rune) {
        return !qualities.isEmpty() && !qualities.contains(rune.getQuality());
    }
}
