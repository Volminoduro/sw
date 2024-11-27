package org.volminoduro.records.translated;

import org.volminoduro.records.translated.enums.Location;
import org.volminoduro.records.translated.enums.Quality;
import org.volminoduro.records.translated.enums.Set;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

import java.util.Collection;

public record Rune(
        int id,
        Location location,
        Quality quality,
        Set set,
        int upgraded,
        MainStat mainStat,
        InnateStat innateStat,
        Collection<SubStat> subStats,
        Monster possessedByMonster) {
}