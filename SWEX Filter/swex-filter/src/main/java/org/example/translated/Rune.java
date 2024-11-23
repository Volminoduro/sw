package org.example.translated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    int upgraded;
    MainStat mainStat = new MainStat();
    InnateStat innateStat = new InnateStat();
    Collection<SubStat> subStats = new ArrayList<>();
    Monster possessedByMonster;

    public Rune() {
    }

}