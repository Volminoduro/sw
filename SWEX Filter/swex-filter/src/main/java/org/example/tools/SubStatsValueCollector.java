package org.example.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json.SubStatValueJSON;
import org.example.key.MappingKey;
import org.example.translated.filter.SubStatValue;
import org.example.translated.stat.SubStat;
import org.example.translated.stat.TypeStat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SubStatsValueCollector {

    // TODO Save keys already used (performance issues)
    private static final Collection<SubStatValue> mappedSubStatsValue = new ArrayList<>();
    private static SubStatsValueCollector instance;
    private static JsonNode mappingJSON;

    private SubStatsValueCollector() throws IOException {
        mappingJSON = new ObjectMapper().readTree(new File("src/main/resources/mapping.json"));
    }

    public static SubStatsValueCollector getInstance() throws IOException {
        if (instance == null) instance = new SubStatsValueCollector();
        return instance;
    }

    public static SubStatValue getSubStatValue(TypeStat typeStat, Integer grade) throws IOException {
        for (SubStatValue subStatValue : mappedSubStatsValue) {
            if (subStatValue.getTypeStat() == typeStat && Objects.equals(subStatValue.getGrade(), grade)) {
                return subStatValue;
            }
        }
        Translator.getInstance();
        SubStatValue subStatValue = Translator.translateSubStatsValueJSON(getSubStatValueJSON(typeStat, grade));
        mappedSubStatsValue.add(subStatValue);
        return subStatValue;
    }

    private static SubStatValueJSON getSubStatValueJSON(TypeStat typeStat, Integer grade) throws IOException {
        JsonNode substatValuesList = mappingJSON.get(MappingKey.RUNE_LIST.value).get(MappingKey.RUNE_SUBSTAT.value);
        Translator.getInstance();

        JsonNode selectedSubStatValue = substatValuesList.get(Translator.getTypeStatIntegerFromTypeStat(typeStat).toString());
        JsonNode maxSubStatValueNode = selectedSubStatValue.get(MappingKey.RUNE_SUBSTAT_MAX.value);
        JsonNode selectedMaxSubStatValue = maxSubStatValueNode.get(grade.toString());
        SubStatValueJSON subStatValueJSON = new SubStatValueJSON();
        subStatValueJSON.setTypeStat(Translator.getTypeStatIntegerFromTypeStat(typeStat));
        subStatValueJSON.setGrade(grade);
        subStatValueJSON.setAmountMax(Integer.parseInt(selectedMaxSubStatValue.asText())); // TODO
        return subStatValueJSON;
    }

    public static Float calculateTotalRelativeEfficiency(Collection<SubStat> subStats, Integer grade) throws IOException {
        Float totalRatio = 0f;
        for (SubStat subStat : subStats) {
            totalRatio = (float) (subStat.getAmount() / getSubStatValue(subStat.getTypeStat(), grade).getMax()) * 100;
        }
        // TODO : Would it be a good idea for the ancient rune to go above 100% ?
        // TODO : efficiency with isolation would be (totalRatio 15 x nb of TypeStat wanted)
        totalRatio -= 160F;
        return totalRatio;
    }
}
