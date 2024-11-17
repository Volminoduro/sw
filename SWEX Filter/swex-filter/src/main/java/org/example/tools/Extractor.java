package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json.MonsterJSON;
import org.example.json.RuneJSON;
import org.example.key.JSONKey;
import org.example.translated.Rune;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public final class Extractor {

    private Extractor() {
    }

    public static Collection<Rune> extract(String swexPath, String filterPath) throws IOException {
        Collection<Rune> runes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(swexPath));
        JsonNode monsterList = jsonNode.get(JSONKey.MONSTER_LIST.value);

        for (JsonNode monster : monsterList) {
            MonsterJSON monsterJSON = new MonsterJSON(monster);
            monsterJSON.getRunes().forEach(rune -> runes.add(Translator.translateRuneJSON(rune)));
        }

        runes.addAll(extractRunesFromJsonNodeListOfRunes(jsonNode.get(JSONKey.RUNES.value)));
        return runes;
    }

    public static Collection<Rune> extractRunesFromJsonNodeListOfRunes(JsonNode jsonNode) {
        Collection<Rune> runes = new ArrayList<>();
        jsonNode.forEach(jsonNode1 -> runes.add(Translator.translateRuneJSON(new RuneJSON(jsonNode1))));
        return runes;
    }
}
