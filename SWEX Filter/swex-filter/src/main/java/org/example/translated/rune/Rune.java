package org.example.translated.rune;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.translated.Monster;
import org.example.translated.filter.Filter;
import org.example.translated.stat.InnateStat;
import org.example.translated.stat.MainStat;
import org.example.translated.stat.SubStat;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
public class Rune {

    String id;
    Location location;
    Quality quality;
    Set set;
    int stars;
    int upgraded;
    MainStat mainStat = new MainStat();
    InnateStat innateStat = new InnateStat();
    Collection<SubStat> subStats = new ArrayList<>();
    Monster possessedByMonster;
    Collection<Filter> filtersEligible = new ArrayList<>();
    Collection<Gem> gemsSuitable = new ArrayList<>();
    Collection<Grindstone> grindstonesSuitable = new ArrayList<>();

    public Rune() {
    }

}