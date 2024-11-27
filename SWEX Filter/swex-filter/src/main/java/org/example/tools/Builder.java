package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.key.JSONKey;
import org.example.records.Pair;
import org.example.records.json.MonsterJSON;
import org.example.records.json.RuneJSON;
import org.example.records.json.SubStatJSON;
import org.example.records.translated.Monster;
import org.example.records.translated.Rune;
import org.example.records.translated.enums.TypeStat;
import org.example.records.translated.stat.SubStat;

import java.util.ArrayList;
import java.util.Collection;

public final class Builder {

    private Builder() {
    }

    public static MonsterJSON buildMonsterJSONRecordFromJsonNode(JsonNode jsonNode) {
        int id = jsonNode.get(JSONKey.UNIT_MASTER_ID.value).asInt();
        Collection<RuneJSON> runeJSONS = new ArrayList<>();
        jsonNode.get(JSONKey.RUNES.value).forEach(runeJsonNode -> runeJSONS.add(Builder.buildRuneJSONRecordFromJsonNode(runeJsonNode)));
        return new MonsterJSON(id, runeJSONS);
    }

    public static RuneJSON buildRuneJSONRecordFromJsonNode(JsonNode jsonNode) {
        int id = jsonNode.get(JSONKey.RUNE_ID.value).asInt();
        int slot_no = jsonNode.get(JSONKey.SLOT_NO.value).asInt();
        int rank = jsonNode.get(JSONKey.RANK.value).asInt();
        int set_id = jsonNode.get(JSONKey.SET_ID.value).asInt();
        int upgrade_curr = jsonNode.get(JSONKey.UPGRADE_CURR.value).asInt();

        JsonNode mainStatJsonNode = jsonNode.get(JSONKey.PRI_EFF.value);
        Pair<Integer, Integer> mainStatJSON = new Pair<>(mainStatJsonNode.get(0).asInt(), mainStatJsonNode.get(1).asInt());

        JsonNode innateStatJsonNode = jsonNode.get(JSONKey.PREFIX_EFF.value);
        Pair<Integer, Integer> innateStatJSON = new Pair<>(innateStatJsonNode.get(0).asInt(), innateStatJsonNode.get(1).asInt());

        JsonNode subStatsJsonNode = jsonNode.get(JSONKey.SEC_EFF.value);
        Collection<SubStatJSON> subStatJSONS = new ArrayList<>();
        for (JsonNode jsonNode1 : subStatsJsonNode) {
            SubStatJSON subStatJSON = new SubStatJSON(jsonNode1.get(0).asInt(), jsonNode1.get(1).asInt(), jsonNode1.get(3).asInt(), jsonNode1.get(2).asInt());
            subStatJSONS.add(subStatJSON);
        }
        return new RuneJSON(id, slot_no, rank, set_id, upgrade_curr, mainStatJSON, innateStatJSON, subStatJSONS);
    }

    public static Monster buildMonsterRecordFromMonsterJSON(String monsterName, MonsterJSON monsterJSON) {
        return new Monster(monsterJSON.id(), monsterName);
    }

    public static Rune buildRuneFromRuneJson(RuneJSON runeJSON) {
        return null;
    }

    public static SubStat buildMinimalSubStat(TypeStat typeStat, int amount) {
        return new SubStat(typeStat, amount, false, 0);
    }
}
