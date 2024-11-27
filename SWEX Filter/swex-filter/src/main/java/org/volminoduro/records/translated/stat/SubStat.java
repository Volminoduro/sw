package org.volminoduro.records.translated.stat;

import org.volminoduro.enums.translated.TypeStat;

public record SubStat(TypeStat typeStat, int amount, boolean enchanted, int grind) {
}
