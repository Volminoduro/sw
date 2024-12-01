package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum RuneQuality {

    COMMON("Common", 1),
    MAGIC("Magic", 2),
    RARE("Rare", 3),
    HERO("Hero", 4),
    LEGEND("Legend", 5),
    ANCIENT_COMMON("Ancient Common", 11),
    ANCIENT_MAGIC("Ancient Magic", 12),
    ANCIENT_RARE("Ancient Rare", 13),
    ANCIENT_HERO("Ancient Hero", 14),
    ANCIENT_LEGEND("Ancient Legend", 15);

    private static final Map<String, RuneQuality> BY_LABEL = new HashMap<>();
    private static final Map<Integer, RuneQuality> BY_JSONMAPPINGKEY = new HashMap<>();

    static {
        for (RuneQuality e : values()) {
            BY_LABEL.put(e.label, e);
            BY_JSONMAPPINGKEY.put(e.jsonMappingKey, e);
        }
    }

    public final String label;
    public final int jsonMappingKey;

    RuneQuality(String label, int jsonMappingKey) {
        this.label = label;
        this.jsonMappingKey = jsonMappingKey;
    }

    public static RuneQuality valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static RuneQuality valueOfJsonMappingKey(int jsonMappingKey) {
        return BY_JSONMAPPINGKEY.get(jsonMappingKey);
    }

}
