package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.tools.SWEXMapper;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EfficiencyRelativeSubStatsFilter extends Filter {

    double efficiencyRelativeThreshold = 0;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        double totalRatio = SWEXMapper.calculateTotalRelativeEfficiency(rune.subStats().stream()
                .filter(subStat -> subStats.contains(subStat.typeStat())).toList(), rune.stars());

        return subStats.isEmpty() || totalRatio >= efficiencyRelativeThreshold;
    }
}
