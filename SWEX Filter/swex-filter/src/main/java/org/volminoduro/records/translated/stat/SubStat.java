package org.volminoduro.records.translated.stat;

import org.volminoduro.records.translated.enums.TypeStat;

public record SubStat(TypeStat typeStat, int amount, boolean enchanted, int grind) {
}
