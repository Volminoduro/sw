package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.Pair;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.json.SubStatJSON;
import org.volminoduro.records.json.SubStatValueJSON;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.records.translated.stat.SubStatValue;

import java.util.ArrayList;
import java.util.Collection;

public final class Builder {

    private Builder() {
    }

    public static MonsterJSON buildMonsterJSONFromJsonNode(JsonNode jsonNode) {
        int id = jsonNode.get(SWEXFileJSONKey.UNIT_MASTER_ID.value).asInt();
        Collection<RuneJSON> runeJSONS = new ArrayList<>();
        jsonNode.get(SWEXFileJSONKey.RUNES.value).forEach(runeJsonNode -> runeJSONS.add(Builder.buildRuneJSONFromJsonNode(runeJsonNode)));
        return new MonsterJSON(id, runeJSONS);
    }

    public static RuneJSON buildRuneJSONFromJsonNode(JsonNode jsonNode) {
        int id = jsonNode.get(SWEXFileJSONKey.RUNE_ID.value).asInt();
        int slot_no = jsonNode.get(SWEXFileJSONKey.SLOT_NO.value).asInt();
        int stars = jsonNode.get(SWEXFileJSONKey.STARS.value).asInt();
        int rank = jsonNode.get(SWEXFileJSONKey.RANK.value).asInt();
        int set_id = jsonNode.get(SWEXFileJSONKey.SET_ID.value).asInt();
        int upgrade_curr = jsonNode.get(SWEXFileJSONKey.UPGRADE_CURR.value).asInt();

        JsonNode mainStatJsonNode = jsonNode.get(SWEXFileJSONKey.PRI_EFF.value);
        Pair<Integer, Integer> mainStatJSON = new Pair<>(mainStatJsonNode.get(0).asInt(), mainStatJsonNode.get(1).asInt());

        JsonNode innateStatJsonNode = jsonNode.get(SWEXFileJSONKey.PREFIX_EFF.value);
        Pair<Integer, Integer> innateStatJSON = new Pair<>(innateStatJsonNode.get(0).asInt(), innateStatJsonNode.get(1).asInt());

        JsonNode subStatsJsonNode = jsonNode.get(SWEXFileJSONKey.SEC_EFF.value);
        Collection<SubStatJSON> subStatJSONS = new ArrayList<>();
        for (JsonNode jsonNode1 : subStatsJsonNode) {
            SubStatJSON subStatJSON = new SubStatJSON(jsonNode1.get(0).asInt(), jsonNode1.get(1).asInt(), jsonNode1.get(3).asInt(), jsonNode1.get(2).asInt());
            subStatJSONS.add(subStatJSON);
        }
        return new RuneJSON(id, slot_no, stars, rank, set_id, upgrade_curr, mainStatJSON, innateStatJSON, subStatJSONS);
    }

    public static Monster buildMonsterFromMonsterJSON(MonsterJSON monsterJSON) {
        return new Monster(monsterJSON.id(), Mapper.getMonsterName(monsterJSON));
    }

    public static SubStatValue buildSubStatValueFromSubStatValueJSON(SubStatValueJSON subStatValueJSON) {
        return new SubStatValue(TypeStat.valueOfJsonMappingKey(subStatValueJSON.getTypeStat()), subStatValueJSON.getGrade(), subStatValueJSON.getAmountMax());
    }

    public static SubStat buildMinimalSubStat(TypeStat typeStat, int amount) {
        return new SubStat(typeStat, amount, false, 0);
    }
}
