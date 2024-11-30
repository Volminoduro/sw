package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.tools.Mapper;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubStatsRollsNumberFilter extends Filter {

    Integer subStatsRollsNumber;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        return subStats.isEmpty()
                || Mapper.calculateSubStatsTotalRolls(rune, getSubStats()) >= subStatsRollsNumber;
    }

}
