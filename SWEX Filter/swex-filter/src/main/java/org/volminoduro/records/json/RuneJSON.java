package org.volminoduro.records.json;

import org.volminoduro.records.Pair;

import java.util.Collection;

public record RuneJSON(int id,
                       int slot_no,
                       int stars,
                       int rank,
                       int set_id,
                       int upgrade_curr,
                       Pair<Integer, Integer> mainStatJSON,
                       Pair<Integer, Integer> innateStatJSON,
                       Collection<SubStatJSON> subStatsJSON) {
}
