package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum RuneSet {

    Energy("Energy", 1),
    Guard("Guard", 2),
    Swift("Swift", 3),
    Blade("Blade", 4),
    Rage("Rage", 5),
    Focus("Focus", 6),
    Endure("Endure", 7),
    Fatal("Fatal", 8),
    Despair("Despair", 10),
    Vampire("Vampire", 11),
    Violent("Violent", 13),
    Nemesis("Nemesis", 14),
    Will("Will", 15),
    Shield("Shield", 16),
    Revenge("Revenge", 17),
    Destroy("Destroy", 18),
    Fight("Fight", 19),
    Determination("Determination", 20),
    Enhance("Enhance", 21),
    Accuracy("Accuracy", 22),
    Tolerance("Tolerance", 23),
    Seal("Seal", 24),
    Intangible("Intangible", 25),
    Immemorial("Immemorial", 99);

    private static final Map<String, RuneSet> BY_LABEL = new HashMap<>();
    private static final Map<Integer, RuneSet> BY_JSONMAPPINGKEY = new HashMap<>();

    static {
        for (RuneSet e : values()) {
            BY_LABEL.put(e.label, e);
            BY_JSONMAPPINGKEY.put(e.jsonMappingKey, e);
        }
    }

    public final String label;
    public final int jsonMappingKey;

    RuneSet(String label, int jsonMappingKey) {
        this.label = label;
        this.jsonMappingKey = jsonMappingKey;
    }

    public static RuneSet valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static RuneSet valueOfJsonMappingKey(int jsonMappingKey) {
        return BY_JSONMAPPINGKEY.get(jsonMappingKey);
    }

}
