package org.example.records.json;

import java.util.Collection;
import java.util.Map;

public record RuneJSON(int id,
                       int slot_no,
                       int rank,
                       int set_id,
                       int upgrade_curr,
                       Map<Integer, Integer> mainStatJSON,
                       Map<Integer, Integer> innateStatJSON,
                       Collection<SubStatJSON> subStatsJSON) {
}
