package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.example.key.JSONKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class RuneJSON {


    String id;
    String slot_no;
    String rank;
    String set_id;
    String upgrade_curr;
    Map<Integer, Integer> mainStatJSON = new HashMap<>();
    Map<Integer, Integer> innateStatJSON = new HashMap<>();
    Collection<SubStatJSON> subStatsJSON = new ArrayList<>();

    public RuneJSON(JsonNode jsonNode) {
        this.id = jsonNode.get(JSONKey.RUNE_ID.value).asText();
        this.slot_no = jsonNode.get(JSONKey.SLOT_NO.value).asText();
        this.rank = jsonNode.get(JSONKey.RANK.value).asText();
        this.set_id = jsonNode.get(JSONKey.SET_ID.value).asText();
        this.upgrade_curr = jsonNode.get(JSONKey.UPGRADE_CURR.value).asText();

        JsonNode mainStatJsonNode = jsonNode.get(JSONKey.PRI_EFF.value);
        this.mainStatJSON.put(mainStatJsonNode.get(0).asInt(), mainStatJsonNode.get(1).asInt());

        JsonNode innateStatJsonNode = jsonNode.get(JSONKey.PREFIX_EFF.value);
        this.innateStatJSON.put(innateStatJsonNode.get(0).asInt(), innateStatJsonNode.get(1).asInt());

        JsonNode subStatsJsonNode = jsonNode.get(JSONKey.SEC_EFF.value);
        for (JsonNode jsonNode1 : subStatsJsonNode) {
            SubStatJSON subStatJSON = new SubStatJSON();
            subStatJSON.setTypeStat(jsonNode1.get(0).asInt());
            subStatJSON.setAmount(jsonNode1.get(1).asInt());
            subStatJSON.setEnchant(jsonNode1.get(2).asInt());
            subStatJSON.setGrind(jsonNode1.get(3).asInt());

            this.subStatsJSON.add(subStatJSON);
        }
    }

}
