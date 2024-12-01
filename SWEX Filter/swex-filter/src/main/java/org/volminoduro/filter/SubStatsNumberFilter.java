package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.SubStat;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubStatsNumberFilter extends Filter {

    Integer subStatsPresenceNumber = 0;

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
