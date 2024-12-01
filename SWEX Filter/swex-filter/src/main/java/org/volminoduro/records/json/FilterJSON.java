package org.volminoduro.records.json;

import org.volminoduro.records.Pair;

import java.util.Collection;

public record FilterJSON(String name, Pair<String, Integer> type, Collection<String> sets, Collection<String> quality,
                         Collection<String> stars, Collection<String> upgraded, Collection<String> slots,
                         Collection<String> main_stat,
                         Collection<String> innate_stat, Collection<String> sub_stat) {
}
