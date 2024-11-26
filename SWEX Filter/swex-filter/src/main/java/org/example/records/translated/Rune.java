package org.example.records.translated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.records.translated.enums.Location;
import org.example.records.translated.enums.Quality;
import org.example.records.translated.enums.Set;
import org.example.records.translated.stat.InnateStat;
import org.example.records.translated.stat.MainStat;
import org.example.records.translated.stat.SubStat;

import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
public class Rune {

    int id;
    Location location;
    Quality quality;
    Set set;
    int upgraded;
    MainStat mainStat;
    InnateStat innateStat;
    Collection<SubStat> subStats;
    Monster possessedByMonster;

    public Rune() {
    }

}