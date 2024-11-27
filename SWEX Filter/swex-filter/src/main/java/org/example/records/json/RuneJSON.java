package org.example.records.json;

import org.example.records.Pair;

import java.util.Collection;

public record RuneJSON(int id,
                       int slot_no,
                       int rank,
                       int set_id,
                       int upgrade_curr,
                       Pair<Integer, Integer> mainStatJSON,
                       Pair<Integer, Integer> innateStatJSON,
                       Collection<SubStatJSON> subStatsJSON) {
}
