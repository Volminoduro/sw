package org.example.key;

public enum SWEXFileJSONKey {

    MONSTER_LIST("unit_list"),
    UNIT_MASTER_ID("unit_master_id"),
    RUNE_ID("rune_id"),
    RUNES("runes"),
    SLOT_NO("slot_no"),
    SET_ID("set_id"),
    RANK("rank"),
    UPGRADE_CURR("upgrade_curr"), PRI_EFF("pri_eff"), PREFIX_EFF("prefix_eff"), SEC_EFF("sec_eff"), CLASS("class");

    public final String value;

    SWEXFileJSONKey(String value) {
        this.value = value;
    }
}
