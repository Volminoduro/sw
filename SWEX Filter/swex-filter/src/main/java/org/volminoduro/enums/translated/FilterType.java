package org.volminoduro.enums.translated;

import java.util.HashMap;
import java.util.Map;

public enum FilterType {

    TYPE_MODE_EFFICIENCY("efficiency"), TYPE_MODE_SUBSTATS("substats");

    private static final Map<String, FilterType> BY_LABEL = new HashMap<>();

    static {
        for (FilterType e : values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public final String label;

    FilterType(String label) {
        this.label = label;
    }

    public static FilterType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

}
