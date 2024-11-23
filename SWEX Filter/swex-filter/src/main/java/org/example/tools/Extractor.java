package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json.MonsterJSON;
import org.example.json.RuneJSON;
import org.example.key.JSONKey;
import org.example.translated.filter.Filter;
import org.example.translated.rune.Rune;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class Extractor {

    private static Collection<Filter> filters;

    private Extractor() {
    }

    public static Collection<Rune> extract(String swexPath, String filterPath) throws IOException {
        Collection<Rune> runes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode filterJsonNode = objectMapper.readTree(new File(filterPath));
        filters = extractFilters(filterJsonNode);

        JsonNode runeJsonNode = objectMapper.readTree(new File(swexPath));
        JsonNode monsterList = runeJsonNode.get(JSONKey.MONSTER_LIST.value);
        runes.addAll(extractRunesFromJsonNodeListOfMonsters(monsterList));
        runes.addAll(extractRunesFromJsonNodeListOfRunes(runeJsonNode.get(JSONKey.RUNES.value)));
        return runes;
    }

    private static Collection<Rune> extractRunesFromJsonNodeListOfMonsters(JsonNode monsterList) throws IOException {
        Collection<Rune> runes = new ArrayList<>();
        for (JsonNode monster : monsterList) {
            MonsterJSON monsterJSON = new MonsterJSON(monster);
            Translator.getInstance();
            monsterJSON.getRunes().forEach(runeJSON -> {
                Rune rune = Translator.translateRuneJSON(runeJSON, monsterJSON);
                for (Filter filter : filters) {
                    if (filter.isEligible(rune)) {
                        rune.getFiltersEligible().add(filter);
                    }
                }
                if (!rune.getFiltersEligible().isEmpty()) {
                    runes.add(rune);
                }
            });
        }
        return runes;
    }

    public static Collection<Rune> extractRunesFromJsonNodeListOfRunes(JsonNode jsonNode) throws IOException {
        Collection<Rune> runes = new ArrayList<>();
        Translator.getInstance();
        jsonNode.forEach(runeJsonNode -> {
            Rune rune = Translator.translateRuneJSON(new RuneJSON(runeJsonNode));
            for (Filter filter : filters) {
                if (filter.isEligible(rune)) {
                    rune.getFiltersEligible().add(filter);
                }
            }
            if (!rune.getFiltersEligible().isEmpty()) {
                runes.add(rune);
            }
        });
        return runes;
    }

    public static Collection<Filter> extractFilters(JsonNode jsonNode) {
        // TODO Single instanceof the Filters
        return Collections.EMPTY_LIST;
    }
}
