package org.example.translated.stat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Stat {
    TypeStat typeStat;
    int amount;
}

