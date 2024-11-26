package org.example.records.translated.enums;

public enum TypeStat {

    ATK_FLAT("ATK +"),
    HP_FLAT("HP +"),
    DEF_FLAT("DEF +"),
    HP_PERCENT("HP %"),
    ATK_PERCENT("ATK %"),
    DEF_PERCENT("DEF %"),
    SPD("SPD"),
    CRATE("CRate"),
    CDMG("CDmg"),
    RES("RES"),
    ACC("ACC");

    public final String value;

    TypeStat(String value) {
        this.value = value;
    }
}
