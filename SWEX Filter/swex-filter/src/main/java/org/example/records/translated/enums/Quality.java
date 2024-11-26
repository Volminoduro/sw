package org.example.records.translated.enums;

public enum Quality {

    COMMON("Common"),
    MAGIC("Magic"),
    RARE("Rare"),
    HERO("Hero"),
    LEGEND("Legend"),
    ANCIENT_COMMON("Common"),
    ANCIENT_MAGIC("Magic"),
    ANCIENT_RARE("Rare"),
    ANCIENT_HERO("Hero"),
    ANCIENT_LEGEND("Legend");

    public final String value;

    Quality(String value) {
        this.value = value;
    }
}
