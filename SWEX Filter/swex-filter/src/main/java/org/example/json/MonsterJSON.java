package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.example.key.SWEXFileJSONKey;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class MonsterJSON {

    String id;
    Collection<RuneJSON> runesJSON = new ArrayList<>();

    public MonsterJSON(JsonNode jsonNode) {
        this.id = jsonNode.get(SWEXFileJSONKey.UNIT_MASTER_ID.value).asText();
        jsonNode.get(SWEXFileJSONKey.RUNES.value).forEach(runeJSON -> runesJSON.add(new RuneJSON(runeJSON)));
    }

    public Collection<RuneJSON> getRunes() {
        return runesJSON;
    }

}
