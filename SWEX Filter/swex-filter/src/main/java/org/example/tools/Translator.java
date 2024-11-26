package org.example.tools;

import org.example.records.translated.enums.Location;
import org.example.records.translated.enums.Quality;
import org.example.records.translated.enums.Set;
import org.example.records.translated.enums.TypeStat;

public final class Translator {

    private Translator() {

    }

    public static Quality getQualityFromQualityId(int rank) {
        return switch (rank) {
            case 1 -> Quality.COMMON;
            case 2 -> Quality.MAGIC;
            case 3 -> Quality.RARE;
            case 4 -> Quality.HERO;
            case 5 -> Quality.LEGEND;
            case 11 -> Quality.ANCIENT_COMMON;
            case 12 -> Quality.ANCIENT_MAGIC;
            case 13 -> Quality.ANCIENT_RARE;
            case 14 -> Quality.ANCIENT_HERO;
            case 15 -> Quality.ANCIENT_LEGEND;
            default -> throw new IllegalStateException("Unexpected value: " + rank);
        };
    }

    public static TypeStat getTypeStatFromTypeStatInteger(Integer typeStatInteger) {
        return switch (typeStatInteger) {
            case 1 -> TypeStat.HP_FLAT;
            case 2 -> TypeStat.HP_PERCENT;
            case 3 -> TypeStat.ATK_FLAT;
            case 4 -> TypeStat.ATK_PERCENT;
            case 5 -> TypeStat.DEF_FLAT;
            case 6 -> TypeStat.DEF_PERCENT;
            case 8 -> TypeStat.SPD;
            case 9 -> TypeStat.CRATE;
            case 10 -> TypeStat.CDMG;
            case 11 -> TypeStat.RES;
            case 12 -> TypeStat.ACC;
            default -> throw new IllegalStateException("Unexpected value: " + typeStatInteger);
        };
    }

    public static Set getSetFromSetId(int setId) {
        return switch (setId) {
            case 1 -> Set.Energy;
            case 2 -> Set.Guard;
            case 3 -> Set.Swift;
            case 4 -> Set.Blade;
            case 5 -> Set.Rage;
            case 6 -> Set.Focus;
            case 7 -> Set.Endure;
            case 8 -> Set.Fatal;
            case 10 -> Set.Despair;
            case 11 -> Set.Vampire;
            case 13 -> Set.Violent;
            case 14 -> Set.Nemesis;
            case 15 -> Set.Will;
            case 16 -> Set.Shield;
            case 17 -> Set.Revenge;
            case 18 -> Set.Destroy;
            case 19 -> Set.Fight;
            case 20 -> Set.Determination;
            case 21 -> Set.Enhance;
            case 22 -> Set.Accuracy;
            case 23 -> Set.Tolerance;
            case 24 -> Set.Seal;
            case 25 -> Set.Intangible;
            case 99 -> Set.Immemorial;
            default -> throw new IllegalStateException("Unexpected value: " + setId);
        };
    }

    public static Location getLocationFromLocationId(int slotNo) {
        return switch (slotNo) {
            case 1 -> Location.SLOT_1;
            case 2 -> Location.SLOT_2;
            case 3 -> Location.SLOT_3;
            case 4 -> Location.SLOT_4;
            case 5 -> Location.SLOT_5;
            case 6 -> Location.SLOT_6;
            default -> throw new IllegalStateException("Unexpected value: " + slotNo);
        };
    }
}
