package org.example.translated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubStat extends Stat {
    int grind = 0;
    boolean enchanted = false;
}
