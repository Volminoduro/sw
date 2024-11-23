package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.example.key.JSONKey;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class MonsterJSON {

    int id;
    Collection<RuneJSON> runesJSON = new ArrayList<>();

    public MonsterJSON(JsonNode jsonNode) {
        this.id = jsonNode.get(JSONKey.UNIT_ID.value).asInt();
        jsonNode.get(JSONKey.RUNES.value).forEach(runeJSON -> runesJSON.add(new RuneJSON(runeJSON)));
    }

    public Collection<RuneJSON> getRunes() {
        return runesJSON;
    }

}
