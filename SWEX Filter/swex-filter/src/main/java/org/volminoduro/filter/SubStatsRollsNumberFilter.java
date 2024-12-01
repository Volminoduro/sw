package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.records.translated.Rune;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubStatsRollsNumberFilter extends Filter {

    Integer subStatsRollsNumber;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        throw new RuntimeException("Don't use SubStatsRollsNumberFilter, not ready yet !");
//        return subStats.isEmpty()
//                || Mapper.calculateSubStatsTotalRolls(rune, getSubStats()) >= subStatsRollsNumber;
    }

}
