package org.volminoduro.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.tools.Mapper;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EfficiencyRelativeSubStatsFilter extends Filter {

    double efficiencyRelativeThreshold;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        double totalRatio = Mapper.calculateTotalRelativeEfficiency(rune.subStats().stream()
                .filter(subStat -> subStats.contains(subStat.typeStat())).toList(), rune.stars());

        return subStats.isEmpty() || totalRatio >= efficiencyRelativeThreshold;
    }
}
