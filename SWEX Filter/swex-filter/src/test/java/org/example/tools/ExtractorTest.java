package org.example.tools;

import org.example.translated.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractorTest {

    @Test
    void extractRuneEquippedToMonster() throws IOException {
        Collection<Rune> runes = Extractor.extract("src/test/resources/runes.json", "");
        Rune rune = new Rune();
        rune.setId("123");
        rune.setLocation(Location.SLOT_1);
        rune.setSet(Set.Fight);
        rune.setQuality(Quality.LEGEND);
        rune.setUpgraded(15);

        MainStat mainStat = new MainStat();
        mainStat.setTypeStat(TypeStat.ATK_FLAT);
        mainStat.setAmount(160);
        rune.setMainStat(mainStat);

        InnateStat innateStat = new InnateStat();
        innateStat.setTypeStat(TypeStat.CRATE);
        innateStat.setAmount(6);
        rune.setInnateStat(innateStat);

        SubStat subStat1 = new SubStat();
        subStat1.setTypeStat(TypeStat.HP_PERCENT);
        subStat1.setAmount(8);
        subStat1.setEnchanted(true);

        SubStat subStat2 = new SubStat();
        subStat2.setTypeStat(TypeStat.ACC);
        subStat2.setAmount(19);

        SubStat subStat3 = new SubStat();
        subStat3.setTypeStat(TypeStat.CDMG);
        subStat3.setAmount(18);

        SubStat subStat4 = new SubStat();
        subStat4.setTypeStat(TypeStat.SPD);
        subStat4.setAmount(6);
        subStat4.setEnchanted(true);
        subStat4.setGrind(6);

        rune.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));

        Monster monsterPossessing = new Monster();
        monsterPossessing.setId(23015);
        monsterPossessing.setName("Eirgar");
        rune.setPossessedByMonster(monsterPossessing);
        assertTrue(runes.contains(rune));
    }

    @Test
    void extractRuneUnequippedToMonster() throws IOException {
        Collection<Rune> runes = Extractor.extract("src/test/resources/runes.json", "");
        Rune rune = new Rune();
        rune.setId("321");
        rune.setLocation(Location.SLOT_2);
        rune.setSet(Set.Energy);
        rune.setQuality(Quality.LEGEND);
        rune.setUpgraded(12);

        MainStat mainStat = new MainStat();
        mainStat.setTypeStat(TypeStat.ATK_PERCENT);
        mainStat.setAmount(118);
        rune.setMainStat(mainStat);

        SubStat subStat1 = new SubStat();
        subStat1.setTypeStat(TypeStat.CRATE);
        subStat1.setAmount(4);
        subStat1.setEnchanted(true);

        SubStat subStat2 = new SubStat();
        subStat2.setTypeStat(TypeStat.ACC);
        subStat2.setAmount(11);
        subStat2.setGrind(6);

        SubStat subStat3 = new SubStat();
        subStat3.setTypeStat(TypeStat.HP_PERCENT);
        subStat3.setAmount(18);

        SubStat subStat4 = new SubStat();
        subStat4.setTypeStat(TypeStat.SPD);
        subStat4.setAmount(10);

        rune.setSubStats(Arrays.asList(subStat1, subStat2, subStat3, subStat4));
        assertTrue(runes.contains(rune));
    }
}