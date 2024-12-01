package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.FilterFileJSONKey;
import org.volminoduro.filter.Filter;

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
        JsonNode filtersList = instancedSWEXFileJsonNode.get(FilterFileJSONKey.FILTERS.value);

        for (JsonNode filterJsonNode : filtersList) {
            try {
                filters.add(Builder.buildFilterFromFilterJSON(Builder.buildFilterJSONFromJsonNode(filterJsonNode)));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        return filters;
    }
}
