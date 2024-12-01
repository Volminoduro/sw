package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum RuneSlot {
    SLOT_1(1),
    SLOT_2(2),
    SLOT_3(3),
    SLOT_4(4),
    SLOT_5(5),
    SLOT_6(6);

    private static final Map<Integer, RuneSlot> BY_JSONMAPPINGKEY = new HashMap<>();

    static {
        for (RuneSlot e : values()) {
            BY_JSONMAPPINGKEY.put(e.jsonMappingKey, e);
        }
    }

    public final int jsonMappingKey;

    RuneSlot(int jsonMappingKey) {
        this.jsonMappingKey = jsonMappingKey;
    }

    public static RuneSlot valueOfJsonMappingKey(int jsonMappingKey) {
        return BY_JSONMAPPINGKEY.get(jsonMappingKey);
    }
}
