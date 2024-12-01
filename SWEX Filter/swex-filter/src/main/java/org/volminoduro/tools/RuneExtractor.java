package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.translated.Rune;

import java.util.ArrayList;
import java.util.Collection;

public final class RuneExtractor {

    private static RuneExtractor instance;
    private static JsonNode instancedSWEXFileJsonNode;

    private RuneExtractor() {
    }

    public static void getInstance(JsonNode swexFileJsonNode) {
        if (instance == null) instance = new RuneExtractor();
        instancedSWEXFileJsonNode = swexFileJsonNode;
    }

    public static Collection<Rune> extractAllRunes() {
        Collection<Rune> runes = new ArrayList<>();

        JsonNode monsterList = instancedSWEXFileJsonNode.get(SWEXFileJSONKey.MONSTER_LIST.value);

        for (JsonNode monster : monsterList) {
            MonsterJSON monsterJSON = Builder.buildMonsterJSONFromJsonNode(monster);
            monsterJSON.runesJSON().forEach(rune -> runes.add(SWEXMapper.translateRuneJSON(rune, Builder.buildMonsterFromMonsterJSON(monsterJSON))));
        }

        runes.addAll(extractRunesFromJsonNodeListOfRunes(instancedSWEXFileJsonNode.get(SWEXFileJSONKey.RUNES.value)));


        return runes;
    }

    private static Collection<Rune> extractRunesFromJsonNodeListOfRunes(JsonNode jsonNode) {
        Collection<Rune> runes = new ArrayList<>();
        jsonNode.forEach(runeJsonNode -> runes.add(SWEXMapper.translateRuneJSON(Builder.buildRuneJSONFromJsonNode(runeJsonNode), null)));
        return runes;
    }
}
