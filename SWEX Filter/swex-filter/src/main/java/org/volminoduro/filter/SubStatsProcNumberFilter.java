package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubStatsProcNumberFilter extends Filter {

    Integer subStatsProcNumber;

    // On peut pas faire ça en mathématique sur une seule stat, car si je compare pas à l'absence de proc, je peux mal calculer le truc. C'est-à-dire que 3 procs pourris peuvent donner autant que 2 bons procs.

    @Override
    boolean isSubStatsEligible(Rune rune) {
        return subStats.isEmpty()
                || getTypeStatsMatching(rune) >= subStatsProcNumber;
    }

    private int getTypeStatsMatching(Rune rune) {
        int matchingProcTypesStats = 0;
        for (SubStat subStat : rune.subStats()) {
            if (subStats.contains(subStat.typeStat())) {
                matchingProcTypesStats++;
            }
        }
        return -1;
    }
}
