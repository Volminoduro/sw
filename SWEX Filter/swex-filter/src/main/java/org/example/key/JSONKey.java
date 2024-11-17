package org.example.key;

public enum JSONKey {

    MONSTER_LIST("unit_list"),
    UNIT_ID("unit_id"),
    RUNE_ID("rune_id"),
    RUNES("runes"),
    SLOT_NO("slot_no"),
    SET_ID("set_id"),
    RANK("rank"),
    UPGRADE_CURR("upgrade_curr"), PRI_EFF("prix_eff"), PREFIX_EFF("prefix_eff"), SEC_EFF("sec_eff");

    public final String value;

    JSONKey(String value) {
        this.value = value;
    }
}
