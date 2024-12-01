package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.FilterFileJSONKey;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.enums.translated.FilterType;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.filter.EfficiencyRelativeSubStatsFilter;
import org.volminoduro.filter.Filter;
import org.volminoduro.filter.SubStatsNumberFilter;
import org.volminoduro.records.Pair;
import org.volminoduro.records.json.*;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.records.translated.stat.SubStatValue;
import org.volminoduro.tools.exceptions.FilterNoNameException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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
        Pair<Integer, Integer> mainStatJSON = new Pair<>(mainStatJsonNode.get(0).asInt(),
                mainStatJsonNode.get(1).asInt());

        JsonNode innateStatJsonNode = jsonNode.get(SWEXFileJSONKey.PREFIX_EFF.value);
        Pair<Integer, Integer> innateStatJSON = new Pair<>(innateStatJsonNode.get(0).asInt(),
                innateStatJsonNode.get(1).asInt());

        JsonNode subStatsJsonNode = jsonNode.get(SWEXFileJSONKey.SEC_EFF.value);
        Collection<SubStatJSON> subStatJSONS = new ArrayList<>();
        for (JsonNode jsonNode1 : subStatsJsonNode) {
            SubStatJSON subStatJSON = new SubStatJSON(jsonNode1.get(0).asInt(), jsonNode1.get(1).asInt(),
                    jsonNode1.get(3).asInt(), jsonNode1.get(2).asInt());
            subStatJSONS.add(subStatJSON);
        }
        return new RuneJSON(id, slot_no, stars, rank, set_id, upgrade_curr, mainStatJSON, innateStatJSON, subStatJSONS);
    }

    public static Monster buildMonsterFromMonsterJSON(MonsterJSON monsterJSON) {
        return new Monster(monsterJSON.id(), SWEXMapper.getMonsterName(monsterJSON));
    }

    public static SubStatValue buildSubStatValueFromSubStatValueJSON(SubStatValueJSON subStatValueJSON) {
        return new SubStatValue(TypeStat.valueOfJsonMappingKey(subStatValueJSON.getTypeStat()),
                subStatValueJSON.getGrade(), subStatValueJSON.getAmountMax());
    }

    public static SubStat buildMinimalSubStat(TypeStat typeStat, int amount) {
        return new SubStat(typeStat, amount, false, 0);
    }

    public static FilterJSON buildFilterJSONFromJsonNode(JsonNode jsonNode) {
        String filterJSONName;
        try {
            filterJSONName = jsonNode.get(FilterFileJSONKey.NAME.value).asText();
        } catch (NullPointerException exception) {
            throw new FilterNoNameException("No name json attribute found for filter creation");
        }
        if ((filterJSONName == null || filterJSONName.trim().isEmpty())) {
            throw new FilterNoNameException("Name json attribute is empty or blank for filter creation");
        }
        JsonNode typeJsonNode = jsonNode.get(FilterFileJSONKey.TYPE.value);
        Pair<String, Integer> type = new Pair<>(typeJsonNode.get(FilterFileJSONKey.TYPE_MODE.value).asText(),
                typeJsonNode.get(FilterFileJSONKey.TYPE_VALUE.value).asInt());

        return new FilterJSON(filterJSONName, type,
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.SETS),
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.QUALITIES),
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.STARS),
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.SLOTS),
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.MAIN_STAT),
                extratListOfStringFromAttribute(jsonNode, FilterFileJSONKey.INNATE_STAT),
                extratListOfStringFromAttribute(jsonNode,
                        FilterFileJSONKey.SUB_STAT));
    }

    private static Collection<String> extratListOfStringFromAttribute(JsonNode jsonNode,
                                                                      FilterFileJSONKey filterFileJSONKey) {
        if (jsonNode.get(filterFileJSONKey.value) == null) {
            return Collections.emptyList();
        }
        Collection<String> strings = new ArrayList<>();
        for (JsonNode jsonNode1 : jsonNode.get(filterFileJSONKey.value)) {
            strings.add(jsonNode1.asText());
        }
        return strings;
    }

    public static Filter buildFilterFromFilterJSON(FilterJSON filterJSON) {
        Filter filter;
        if (FilterType.valueOfLabel(filterJSON.type().key()) == FilterType.TYPE_MODE_EFFICIENCY) {
            EfficiencyRelativeSubStatsFilter efficiencyRelativeSubStatsFilter = new EfficiencyRelativeSubStatsFilter();
            efficiencyRelativeSubStatsFilter.setEfficiencyRelativeThreshold(filterJSON.type().value());
            filter = efficiencyRelativeSubStatsFilter;
        }
        if (FilterType.valueOfLabel(filterJSON.type().key()) == FilterType.TYPE_MODE_SUBSTATS) {
            SubStatsNumberFilter subStatsNumberFilter = new SubStatsNumberFilter();
            subStatsNumberFilter.setSubStatsPresenceNumber(filterJSON.type().value());
            filter = subStatsNumberFilter;
        }

        filter.setName(filterJSON.name());
        filter.setRuneSets(filterJSON.sets().stream().collect(RuneSet::valueOfLabel));
        Collectors.

        return filter;
    }
}
