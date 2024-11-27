package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum TypeStat {

    HP_FLAT("HP +", 1),
    HP_PERCENT("HP %", 2),
    ATK_FLAT("ATK +", 3),
    ATK_PERCENT("ATK %", 4),
    DEF_FLAT("DEF +", 5),
    DEF_PERCENT("DEF %", 6),
    SPD("SPD", 8),
    CRATE("CRate", 9),
    CDMG("CDmg", 10),
    RES("RES", 11),
    ACC("ACC", 12);

    private static final Map<String, TypeStat> BY_LABEL = new HashMap<>();
    private static final Map<Integer, TypeStat> BY_JSONMAPPINGKEY = new HashMap<>();

    static {
        for (TypeStat e : values()) {
            BY_LABEL.put(e.label, e);
            BY_JSONMAPPINGKEY.put(e.jsonMappingKey, e);
        }
    }

    public final String label;
    public final int jsonMappingKey;

    TypeStat(String label, int jsonMappingKey) {
        this.label = label;
        this.jsonMappingKey = jsonMappingKey;
    }

    public static TypeStat valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static TypeStat valueOfJsonMappingKey(int jsonMappingKey) {
        return BY_JSONMAPPINGKEY.get(jsonMappingKey);
    }
}
