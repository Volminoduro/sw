package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.key.JSONKey;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.translated.Rune;

import java.util.ArrayList;
import java.util.Collection;

public final class Extractor {

    private static Extractor instance;
    private static JsonNode instancedSWEXFileJsonNode;

    private Extractor() {
    }

    public static void getInstance(JsonNode swexFileJsonNode) {
        if (instance == null) instance = new Extractor();
        instancedSWEXFileJsonNode = swexFileJsonNode;
    }

    public static Collection<Rune> extractAllRunes() {
        Collection<Rune> runes = new ArrayList<>();

        JsonNode monsterList = instancedSWEXFileJsonNode.get(JSONKey.MONSTER_LIST.value);

        for (JsonNode monster : monsterList) {
            MonsterJSON monsterJSON = Builder.buildMonsterJSONFromJsonNode(monster);
            monsterJSON.runesJSON().forEach(rune -> runes.add(Mapper.translateRuneJSON(rune, Builder.buildMonsterFromMonsterJSON(monsterJSON))));
        }

        runes.addAll(extractRunesFromJsonNodeListOfRunes(instancedSWEXFileJsonNode.get(JSONKey.RUNES.value)));
        return runes;
    }

    private static Collection<Rune> extractRunesFromJsonNodeListOfRunes(JsonNode jsonNode) {
        Collection<Rune> runes = new ArrayList<>();
        jsonNode.forEach(runeJsonNode -> runes.add(Mapper.translateRuneJSON(Builder.buildRuneJSONFromJsonNode(runeJsonNode), null)));
        return runes;
    }
}
