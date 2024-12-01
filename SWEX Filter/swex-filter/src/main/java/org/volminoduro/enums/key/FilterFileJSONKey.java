package org.volminoduro.enums.key;

public enum FilterFileJSONKey {

    FILTERS("filters"),
    NAME("name"), TYPE("type"), TYPE_MODE("mode"), TYPE_VALUE("value"), SETS("sets"), QUALITIES("qualities"), STARS(
            "stars"), SLOTS("slots"), MAIN_STAT("main_stat"), INNATE_STAT("innate_stat"), SUB_STAT("sub_stat"),
    UPGRADED("upgraded");

    public final String value;

    FilterFileJSONKey(String value) {
        this.value = value;
    }
}
