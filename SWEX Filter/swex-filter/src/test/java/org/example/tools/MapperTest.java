package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.key.JSONKey;
import org.example.records.json.MonsterJSON;
import org.example.records.json.RuneJSON;
import org.example.records.translated.Monster;
import org.example.records.translated.Rune;
import org.example.records.translated.enums.Location;
import org.example.records.translated.enums.Quality;
import org.example.records.translated.enums.Set;
import org.example.records.translated.enums.TypeStat;
import org.example.records.translated.stat.InnateStat;
import org.example.records.translated.stat.MainStat;
import org.example.records.translated.stat.SubStat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        this.monsterJSON = Builder.buildMonsterJSONRecordFromJsonNode(monsterJSONNode);

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

        this.runeJSON = Builder.buildRuneJSONRecordFromJsonNode(runeJSONNode);
    }

    @Test
    void getMonsterName() {
        assertEquals("Eirgar", Mapper.getMonsterName(monsterJSON));
    }

    @Test
    void translateRuneJSON() {
        Rune excepted = new Rune();
        excepted.setId(321);
        excepted.setLocation(Location.SLOT_2);
        excepted.setSet(Set.Energy);
        excepted.setQuality(Quality.LEGEND);
        excepted.setUpgraded(12);
        excepted.setMainStat(new MainStat(TypeStat.ATK_PERCENT, 118));
        excepted.setInnateStat(new InnateStat(null, 0));
        excepted.setPossessedByMonster(new Monster(23015, "Eirgar"));

        SubStat subStat1 = new SubStat(TypeStat.CRATE, 4, true, 0);
        SubStat subStat2 = new SubStat(TypeStat.ACC, 11, false, 6);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 18);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 10);
        excepted.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        assertEquals(excepted, Mapper.translateRuneJSON(runeJSON, monsterJSON));
    }
}