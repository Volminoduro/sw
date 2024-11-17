package org.example.translated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
public class Rune {

    String id;
    Location location;
    Quality quality;
    Set set;
    int upgraded;
    MainStat mainStat;
    InnateStat innateStat;
    Collection<SubStat> subStats;

    public Rune() {
    }

}
