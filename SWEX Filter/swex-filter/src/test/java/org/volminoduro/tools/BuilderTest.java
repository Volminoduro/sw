package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.key.SWEXFileJSONKey;
import org.volminoduro.records.Pair;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.json.SubStatJSON;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuilderTest {

    @Test
    void buildRuneJSONFromJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subStatsRuneArrayNode = objectMapper.valueToTree(Arrays.asList(
                new int[]{9, 4, 1, 0},
                new int[]{12, 11, 0, 6},
                new int[]{2, 18, 0, 0},
                new int[]{8, 10, 0, 0}));

        ObjectNode runeJSONNode = new ObjectMapper().createObjectNode()
                .put(SWEXFileJSONKey.RUNE_ID.value, 321)
                .put(SWEXFileJSONKey.SLOT_NO.value, 2)
                .put(SWEXFileJSONKey.STARS.value, 6)
                .put(SWEXFileJSONKey.SET_ID.value, 1)
                .put(SWEXFileJSONKey.RANK.value, 5)
                .put(SWEXFileJSONKey.UPGRADE_CURR.value, 12);
        runeJSONNode.set(SWEXFileJSONKey.PRI_EFF.value, objectMapper.valueToTree(new int[]{4, 118}));
        runeJSONNode.set(SWEXFileJSONKey.PREFIX_EFF.value, objectMapper.valueToTree(new int[]{0, 0}));
        runeJSONNode.set(SWEXFileJSONKey.SEC_EFF.value, subStatsRuneArrayNode);

        RuneJSON expected = new RuneJSON(321,
                2, 6, 5, 1, 12, new Pair<>(4, 118),
                new Pair<>(0, 0),
                Arrays.asList(new SubStatJSON(9, 4, 0, 1),
                        new SubStatJSON(12, 11, 6, 0),
                        new SubStatJSON(2, 18, 0, 0),
                        new SubStatJSON(8, 10, 0, 0)));
        assertEquals(expected, Builder.buildRuneJSONFromJsonNode(runeJSONNode));
    }
}