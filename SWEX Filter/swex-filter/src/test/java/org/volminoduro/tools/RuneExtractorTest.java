package org.volminoduro.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.volminoduro.enums.translated.RuneQuality;
import org.volminoduro.enums.translated.RuneSet;
import org.volminoduro.enums.translated.RuneSlot;
import org.volminoduro.enums.translated.TypeStat;
import org.volminoduro.records.translated.Monster;
import org.volminoduro.records.translated.Rune;
import org.volminoduro.records.translated.stat.InnateStat;
import org.volminoduro.records.translated.stat.MainStat;
import org.volminoduro.records.translated.stat.SubStat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RuneExtractorTest {

    private static final String RUNE_TEST_FILE_PATH = "src/test/resources/tools/runeExtractorTest.json";
    private static final String MAPPING_FILE_PATH = "src/main/resources/mapping.json";

    @BeforeAll
    static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RuneExtractor.getInstance(objectMapper.readTree(new File(RUNE_TEST_FILE_PATH)));
        SWEXMapper.initiateInstance(objectMapper.readTree(new File(MAPPING_FILE_PATH)));
    }

    @Test
    void extractRuneEquippedToMonsterAwakened() {
        Collection<Rune> runes = RuneExtractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        Rune expected = new Rune(123,
                RuneSlot.SLOT_1,
                RuneQuality.LEGEND,
                5,
                RuneSet.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(23015, "Eirgar"), Collections.EMPTY_LIST);
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneUnequippedToMonster() {
        Collection<Rune> runes = RuneExtractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.CRATE, 4, true, 0);
        SubStat subStat2 = new SubStat(TypeStat.ACC, 11, false, 6);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.HP_PERCENT, 18);
        SubStat subStat4 = Builder.buildMinimalSubStat(TypeStat.SPD, 10);

        Rune expected = new Rune(321,
                RuneSlot.SLOT_2,
                RuneQuality.LEGEND,
                6,
                RuneSet.Energy,
                12,
                new MainStat(TypeStat.ATK_PERCENT, 118),
                null,
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                null, Collections.EMPTY_LIST);
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneEquippedToMonsterUnawakened() {
        Collection<Rune> runes = RuneExtractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        Rune expected = new Rune(123,
                RuneSlot.SLOT_1,
                RuneQuality.LEGEND,
                5,
                RuneSet.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(12132, "Harpu Fire 2A"), Collections.EMPTY_LIST);
        assertTrue(runes.contains(expected));
    }

    @Test
    void extractRuneEquippedToUnknownMonster() {
        Collection<Rune> runes = RuneExtractor.extractAllRunes();

        SubStat subStat1 = new SubStat(TypeStat.HP_PERCENT, 8, true, 0);
        SubStat subStat2 = Builder.buildMinimalSubStat(TypeStat.ACC, 19);
        SubStat subStat3 = Builder.buildMinimalSubStat(TypeStat.CDMG, 18);
        SubStat subStat4 = new SubStat(TypeStat.SPD, 6, true, 6);

        Rune expected = new Rune(123,
                RuneSlot.SLOT_1,
                RuneQuality.LEGEND,
                5,
                RuneSet.Fight,
                15,
                new MainStat(TypeStat.ATK_FLAT, 160),
                new InnateStat(TypeStat.CRATE, 6),
                Arrays.asList(subStat1, subStat2, subStat3, subStat4),
                new Monster(99939, SWEXMapper.UNKNOWN_MONSTER), Collections.EMPTY_LIST);
        assertTrue(runes.contains(expected));
    }
}