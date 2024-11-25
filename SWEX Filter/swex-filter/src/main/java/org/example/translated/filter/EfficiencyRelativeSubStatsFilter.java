package org.example.translated.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.translated.rune.Rune;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EfficiencyRelativeSubStatsFilter extends Filter {

    Float efficiencyRelativeThreshold;

    @Override
    boolean isSubStatsEligible(Rune rune) {
        return subStats.isEmpty();
    }
}
