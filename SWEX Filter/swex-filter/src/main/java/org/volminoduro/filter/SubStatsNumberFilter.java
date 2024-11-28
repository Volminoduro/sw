package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;

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
        for (SubStat subStat : rune.subStats()) {
            if (subStats.contains(subStat.typeStat())) {
                matchingTypesStats++;
            }
        }
        return matchingTypesStats;
    }

}
