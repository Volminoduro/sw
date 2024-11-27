package org.volminoduro.key;

public enum MappingKey {

    MONSTER_LIST("monster"),
    MONSTER_ATTRIBUTES("attributes"),
    MONSTER_NAMES("names");

    public final String value;

    MappingKey(String value) {
        this.value = value;
    }
}
