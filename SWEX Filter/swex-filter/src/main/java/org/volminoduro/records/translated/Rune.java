package org.volminoduro.records.translated;

import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
import org.volminoduro.filter.Filter;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

import java.util.Collection;

public record Rune(
        int id,
        RuneSlot slot,
        RuneQuality quality,
        int stars,
        RuneSet set,
        int upgraded,
        MainStat mainStat,
        InnateStat innateStat,
        Collection<SubStat> subStats,
        Monster possessedByMonster,
        Collection<Filter> filtersPassed) {
}