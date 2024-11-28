package org.volminoduro.enums.key;

public enum MappingKey {

    MONSTER_LIST("monster"),
    MONSTER_ATTRIBUTES("attributes"),
    MONSTER_NAMES("names"),
    RUNE_LIST("rune"),
    RUNE_SUBSTAT("substat"),
    RUNE_SUBSTAT_MAX("max");

    public final String value;

    MappingKey(String value) {
        this.value = value;
    }
}
