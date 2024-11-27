package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum Set {

    Energy(1 ),
    Guard(2 ),
    Swift(3 ),
    Blade(4 ),
    Rage(5 ),
    Focus(6 ),
    Endure(7 ),
    Fatal(8 ),
    Despair(10),
    Vampire(11),
    Violent(13),
    Nemesis(14),
    Will(15),
    Shield(16),
    Revenge(17),
    Destroy(18),
    Fight(19),
    Determination(20),
    Enhance(21),
    Accuracy(22),
    Tolerance(23),
    Seal(24),
    Intangible(25),
    Immemorial(99);

    private static final Map<Integer, Set> BY_JSONMAPPINGKEY = new HashMap<>();

    static {
        for (Set e : values()) {
            BY_JSONMAPPINGKEY.put(e.jsonMappingKey, e);
        }
    }

    public final int jsonMappingKey;

    Set(int jsonMappingKey) {
        this.jsonMappingKey = jsonMappingKey;
    }

    public static Set valueOfJsonMappingKey(int jsonMappingKey) {
        return BY_JSONMAPPINGKEY.get(jsonMappingKey);
    }

}
