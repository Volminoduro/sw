package org.example.key;

public enum MappingKey {

    MONSTER_LIST("monster"),
    MONSTER_NAMES("names");

    public final String value;

    MappingKey(String value) {
        this.value = value;
    }
}
