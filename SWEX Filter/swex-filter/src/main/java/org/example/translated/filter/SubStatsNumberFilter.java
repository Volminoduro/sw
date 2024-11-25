package org.example.translated.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.translated.rune.Rune;
import org.example.translated.stat.SubStat;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubStatsNumberFilter extends Filter {

    Integer subStatsPresenceNumber;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        return subStats.isEmpty()
                || getTypeStatsMatching(rune) >= subStatsPresenceNumber;
    }

    private int getTypeStatsMatching(Rune rune) {
        int matchingTypesStats = 0;
        for (SubStat subStat : rune.getSubStats()) {
            if (subStats.contains(subStat.getTypeStat())) {
                matchingTypesStats++;
            }
        }
        return matchingTypesStats;
    }

}
