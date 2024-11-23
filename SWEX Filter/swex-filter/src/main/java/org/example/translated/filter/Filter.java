package org.example.translated.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.translated.rune.Quality;
import org.example.translated.rune.Rune;
import org.example.translated.rune.Set;
import org.example.translated.stat.TypeStat;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Filter {

    Collection<Set> sets;
    Collection<Quality> qualities;
    Collection<Integer> stars;
    Collection<TypeStat> mainProperties;
    Collection<Integer> slot;
    Collection<TypeStat> subStats;
    boolean selectGrindstoneFromInventory;
    boolean selectGemFromInventory;

    public boolean isEligible(Rune rune) {
        return false;
    }
}
