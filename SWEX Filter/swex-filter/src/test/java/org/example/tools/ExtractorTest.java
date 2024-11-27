package org.example.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractorTest {

    private static final String RUNE_TEST_FILE_PATH = "src/test/resources/tools/extractorTest-runes.json";
    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";

    @BeforeAll
    static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Extractor.getInstance(objectMapper.readTree(new File(RUNE_TEST_FILE_PATH)));
        Mapper.getInstance(new ObjectMapper().readTree(new File(MAPPING_FILE_PATH)));
    }


    @Test
    void extractRuneEquippedToMonsterAwakened() {
        Collection<Rune> runes = Extractor.extractAllRunes();
        Rune rune = new Rune();
        rune.setId(123);
        rune.setLocation(Location.SLOT_1);
        rune.setSet(Set.Fight);
        rune.setQuality(Quality.LEGEND);
        rune.setUpgraded(15);
        rune.setMainStat(new MainStat(TypeStat.ATK_FLAT, 160));
        rune.setInnateStat(new InnateStat(TypeStat.CRATE, 6));
        rune.setPossessedByMonster(new Monster(23015, "Eirgar"));

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        rune.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        assertTrue(runes.contains(rune));
    }

    @Test
    void extractRuneUnequippedToMonster() {
        Collection<Rune> runes = Extractor.extractAllRunes();
        Rune rune = new Rune();
        rune.setId(321);
        rune.setLocation(Location.SLOT_2);
        rune.setSet(Set.Energy);
        rune.setQuality(Quality.LEGEND);
        rune.setUpgraded(12);
        rune.setMainStat(new MainStat(TypeStat.ATK_PERCENT, 118));

        SubStat subStat1 = new SubStat(TypeStat.CRATE, 4, true, 0);
        SubStat subStat2 = new SubStat(TypeStat.ACC, 11, false, 6);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 18);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 10);

        rune.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));
        assertTrue(runes.contains(rune));
    }

    @Test
    void extractRuneEquippedToMonsterUnawakened() {
        Collection<Rune> runes = Extractor.extractAllRunes();
        Rune expected = new Rune();
        expected.setId(123);
        expected.setLocation(Location.SLOT_1);
        expected.setSet(Set.Fight);
        expected.setQuality(Quality.LEGEND);
        expected.setUpgraded(15);
        expected.setMainStat(new MainStat(TypeStat.ATK_FLAT, 160));
        expected.setInnateStat(new InnateStat(TypeStat.CRATE, 6));
        expected.setPossessedByMonster(new Monster(12132, "Harpu Fire 2A"));

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        expected.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneEquippedToUnknownMonster() {
        Collection<Rune> runes = Extractor.extractAllRunes();
        Rune expected = new Rune();
        expected.setId(123);
        expected.setLocation(Location.SLOT_1);
        expected.setSet(Set.Fight);
        expected.setQuality(Quality.LEGEND);
        expected.setUpgraded(15);
        expected.setMainStat(new MainStat(TypeStat.ATK_FLAT, 160));
        expected.setInnateStat(new InnateStat(TypeStat.CRATE, 6));
        expected.setPossessedByMonster(new Monster(99939, Mapper.UNKNOWN_MONSTER));

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        expected.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        assertTrue(runes.contains(expected));
    }
}