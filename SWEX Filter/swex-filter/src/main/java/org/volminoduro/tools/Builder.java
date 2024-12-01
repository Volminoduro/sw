package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.FilterFileJSONKey;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.enums.translated.*;
import org.volminoduro.filter.EfficiencyRelativeSubStatsFilter;
import org.volminoduro.filter.Filter;
import org.volminoduro.filter.SubStatsNumberFilter;
import org.volminoduro.records.Pair;
import org.volminoduro.records.json.*;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.stat.SubStat;
import org.volminoduro.records.translated.stat.SubStatValue;
import org.volminoduro.tools.exceptions.FilterNoNameException;
import org.volminoduro.tools.exceptions.FilterNoTypeException;

import java.util.*;

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
        String filterJSONName = jsonNode.get(FilterFileJSONKey.NAME.value) != null ?
                jsonNode.get(FilterFileJSONKey.NAME.value).asText() : null;
        JsonNode typeJsonNode = jsonNode.get(FilterFileJSONKey.TYPE.value);
        String typeMode = typeJsonNode.get(FilterFileJSONKey.TYPE_MODE.value) != null ?
                typeJsonNode.get(FilterFileJSONKey.TYPE_MODE.value).asText() : null;
        Integer typeValue = typeJsonNode.get(FilterFileJSONKey.TYPE_VALUE.value) != null ?
                typeJsonNode.get(FilterFileJSONKey.TYPE_VALUE.value).asInt() : null;
        Pair<String, Integer> type = new Pair<>(typeMode,
                typeValue);

        return new FilterJSON(filterJSONName, type,
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.SETS),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.QUALITIES),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.STARS),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.UPGRADED),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.SLOTS),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.MAIN_STAT),
                extractListOfStringFromAttribute(jsonNode, FilterFileJSONKey.INNATE_STAT),
                extractListOfStringFromAttribute(jsonNode,
                        FilterFileJSONKey.SUB_STAT));
    }

    private static Collection<String> extractListOfStringFromAttribute(JsonNode jsonNode,
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
        if (filterJSON.name() == null || filterJSON.name().trim().isEmpty()) {
            throw new FilterNoNameException("No name json attribute found or empty/blank for filter creation");
        }

        if (filterJSON.type() == null || (FilterType.valueOfLabel(filterJSON.type().key()) !=
                FilterType.TYPE_MODE_EFFICIENCY && FilterType.valueOfLabel(filterJSON.type().key()) != FilterType.TYPE_MODE_SUBSTATS)) {
            throw new FilterNoTypeException("No valid input for filter with name : " + filterJSON.name());
        }

        Filter filter = null;
        int value = filterJSON.type().value() != null ? filterJSON.type().value() : 0;
        if (FilterType.valueOfLabel(filterJSON.type().key()) == FilterType.TYPE_MODE_EFFICIENCY) {
            EfficiencyRelativeSubStatsFilter efficiencyRelativeSubStatsFilter = new EfficiencyRelativeSubStatsFilter();
            efficiencyRelativeSubStatsFilter.setEfficiencyRelativeThreshold(value);
            filter = efficiencyRelativeSubStatsFilter;
        }
        if (FilterType.valueOfLabel(filterJSON.type().key()) == FilterType.TYPE_MODE_SUBSTATS) {
            SubStatsNumberFilter subStatsNumberFilter = new SubStatsNumberFilter();
            subStatsNumberFilter.setSubStatsPresenceNumber(value);
            filter = subStatsNumberFilter;
        }

        filter.setName(filterJSON.name());
        if (filterJSON.sets() != null && !filterJSON.sets().isEmpty()) {
            filter.setRuneSets(filterJSON.sets().stream().map(RuneSet::valueOfLabel).toList());
            if (filterJSON.sets().contains("ALL")) filter.setRuneSets(Arrays.stream(RuneSet.values()).toList());
        }
        if (filterJSON.stars() != null && !filterJSON.stars().isEmpty()) {
            filter.setStars(filterJSON.stars().stream().filter(s -> !s.equals("ALL")).map(Integer::valueOf).toList());
            if (filterJSON.stars().contains("ALL")) filter.setStars(List.of(1, 2, 3, 4, 5, 6));
        }
        if (filterJSON.upgraded() != null && !filterJSON.upgraded().isEmpty()) {
            filter.setUpgraded(filterJSON.upgraded().stream().filter(s -> !s.equals("ALL")).map(Integer::valueOf).toList());
            if (filterJSON.upgraded().contains("ALL")) filter.setUpgraded(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                    13, 14, 15));
        }
        if (filterJSON.quality() != null && !filterJSON.quality().isEmpty()) {
            filter.setQualities(filterJSON.quality().stream().map(RuneQuality::valueOfLabel).toList());
            if (filterJSON.quality().contains("ALL")) filter.setQualities(Arrays.stream(RuneQuality.values()).toList());
        }
        if (filterJSON.slots() != null && !filterJSON.slots().isEmpty()) {
            filter.setSlot(filterJSON.slots().stream().filter(s -> !s.equals("ALL")).map(jsonMappingKey -> RuneSlot.valueOfJsonMappingKey
                    (Integer.parseInt(jsonMappingKey))).toList());
            if (filterJSON.slots().contains("ALL")) filter.setSlot(Arrays.stream(RuneSlot.values()).toList());
        }
        if (filterJSON.main_stat() != null && !filterJSON.main_stat().isEmpty()) {
            filter.setMainStats(filterJSON.main_stat().stream().map(TypeStat::valueOfLabel).toList());
            if (filterJSON.main_stat().contains("ALL")) filter.setMainStats(Arrays.stream(TypeStat.values()).toList());
        }
        if (filterJSON.innate_stat() != null && !filterJSON.innate_stat().isEmpty()) {
            filter.setInnateStats(filterJSON.innate_stat().stream().map(TypeStat::valueOfLabel).toList());
            if (filterJSON.innate_stat().contains("ALL"))
                filter.setInnateStats(Arrays.stream(TypeStat.values()).toList());
        }
        if (filterJSON.sub_stat() != null && !filterJSON.sub_stat().isEmpty()) {
            filter.setSubStats(filterJSON.sub_stat().stream().map(TypeStat::valueOfLabel).toList());
            if (filterJSON.sub_stat().contains("ALL")) filter.setSubStats(Arrays.stream(TypeStat.values()).toList());
        }

        return filter;
    }
}
