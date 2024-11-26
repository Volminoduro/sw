package org.volminoduro.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.key.JSONKey;
import org.volminoduro.records.json.MonsterJSON;
import org.volminoduro.records.json.RuneJSON;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.enums.translated.Location;
import org.volminoduro.enums.translated.Quality;
import org.volminoduro.enums.translated.Set;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";
    MonsterJSON monsterJSON;
    RuneJSON runeJSON;

    @BeforeAll
    static void setUpOnce() throws IOException {
        Mapper.getInstance(new ObjectMapper().readTree(new File(MAPPING_FILE_PATH)));
    }

    @BeforeEach
    void setUp() {
        JsonNode monsterJSONNode = new ObjectMapper().createObjectNode()
                .put(JSONKey.UNIT_MASTER_ID.value, "23015")
                .put(JSONKey.RUNES.value, "[]");
        this.monsterJSON = Builder.buildMonsterJSONFromJsonNode(monsterJSONNode);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subStatsRuneArrayNode = objectMapper.valueToTree(Arrays.asList(
                new int[]{9, 4, 1, 0},
                new int[]{12, 11, 0, 6},
                new int[]{2, 18, 0, 0},
                new int[]{8, 10, 0, 0}));

        ObjectNode runeJSONNode = new ObjectMapper().createObjectNode()
                .put(JSONKey.RUNE_ID.value, 321)
                .put(JSONKey.SLOT_NO.value, 2)
                .put(JSONKey.SET_ID.value, 1)
                .put(JSONKey.RANK.value, 5)
                .put(JSONKey.UPGRADE_CURR.value, 12);
        runeJSONNode.set(JSONKey.PRI_EFF.value, objectMapper.valueToTree(new int[]{4, 118}));
        runeJSONNode.set(JSONKey.PREFIX_EFF.value, objectMapper.valueToTree(new int[]{0, 0}));
        runeJSONNode.set(JSONKey.SEC_EFF.value, subStatsRuneArrayNode);

        this.runeJSON = Builder.buildRuneJSONFromJsonNode(runeJSONNode);
    }

    @Test
    void getMonsterName() {
        assertEquals("Eirgar", Mapper.getMonsterName(monsterJSON));
    }

    @Test
    void translateRuneJSON() {
        SubStat subStat1 = new SubStat(TypeStat.CRATE, 4, true, 0);
        SubStat subStat2 = new SubStat(TypeStat.ACC, 11, false, 6);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 18);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 10);

        Rune expected = new Rune(321,
                Location.SLOT_2,
                Quality.LEGEND,
                Set.Energy,
                12,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"));
        assertEquals(expected, Mapper.translateRuneJSON(runeJSON, Builder.buildMonsterFromMonsterJSON(monsterJSON)));
    }
}