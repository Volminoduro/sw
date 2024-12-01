package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.filter.Filter;
import org.volminoduro.records.json.MonsterJSON;

import java.util.ArrayList;
import java.util.Collection;

public class FilterExtractor {

    private static FilterExtractor instance;
    private static JsonNode instancedSWEXFileJsonNode;

    private FilterExtractor() {
    }

    public static void getInstance(JsonNode filterFileJsonNode) {
        if (instance == null) instance = new FilterExtractor();
        instancedSWEXFileJsonNode = filterFileJsonNode;
    }

    public static Collection<Filter> extractAllFilters() {
        Collection<Filter> filters = new ArrayList<>();

        JsonNode filtersList = instancedSWEXFileJsonNode.get(SWEXFileJSONKey.MONSTER_LIST.value);

        for (JsonNode monster : filtersList) {
            MonsterJSON monsterJSON = Builder.buildMonsterJSONFromJsonNode(monster);
//            monsterJSON.runesJSON().forEach(rune -> filters.add(Mapper.translateRuneJSON(rune, Builder.buildMonsterFromMonsterJSON(monsterJSON))));
        }

        return filters;
    }
}
