package org.example.records.translated.stat;

import org.example.records.translated.enums.TypeStat;

public record SubStat(TypeStat typeStat, int amount, boolean enchanted, int grind) {
}
