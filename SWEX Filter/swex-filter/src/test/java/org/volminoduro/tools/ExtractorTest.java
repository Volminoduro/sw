package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.enums.Location;
import org.volminoduro.records.translated.enums.Quality;
import org.volminoduro.records.translated.enums.Set;
import org.volminoduro.records.translated.enums.TypeStat;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

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

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        Rune expected = new Rune(123,
                Location.SLOT_1,
                Quality.LEGEND,
                Set.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"));
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneUnequippedToMonster() {
        Collection<Rune> runes = Extractor.extractAllRunes();

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
                null);
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneEquippedToMonsterUnawakened() {
        Collection<Rune> runes = Extractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);


        Rune expected = new Rune(123,
                Location.SLOT_1,
                Quality.LEGEND,
                Set.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(12132, "Harpu Fire 2A"));
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneEquippedToUnknownMonster() {
        Collection<Rune> runes = Extractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        Rune expected = new Rune(123,
                Location.SLOT_1,
                Quality.LEGEND,
                Set.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(99939, Mapper.UNKNOWN_MONSTER));
        assertTrue(runes.contains(expected));
    }
}