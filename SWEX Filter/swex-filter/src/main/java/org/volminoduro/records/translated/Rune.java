package org.volminoduro.records.translated;

import org.volminoduro.enums.translated.Location;
import org.volminoduro.enums.translated.Quality;
import org.volminoduro.enums.translated.Set;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

import java.util.Collection;

public record Rune(
        int id,
        Location location,
        Quality quality,
        int stars,
        Set set,
        int upgraded,
        MainStat mainStat,
        InnateStat innateStat,
        Collection<SubStat> subStats,
        Monster possessedByMonster) {
}