package org.example.translated.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.tools.SubStatsValueCollector;
import org.example.translated.rune.Rune;

import java.io.IOException;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EfficiencyRelativeSubStatsFilter extends Filter {

    Float efficiencyRelativeThreshold;

    @Override
    boolean isSubStatsEligible(Rune rune) throws IOException {
        SubStatsValueCollector.getInstance();
        Float totalRatio = SubStatsValueCollector.calculateTotalRelativeEfficiency(rune.getSubStats().stream()
                .filter(subStat -> subStats.contains(subStat.getTypeStat())).toList(), rune.getStars());

        return subStats.isEmpty() || totalRatio >= efficiencyRelativeThreshold;
    }
}
