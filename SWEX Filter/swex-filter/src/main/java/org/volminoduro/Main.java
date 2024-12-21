package org.volminoduro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.volminoduro.tools.FilterExtractor;
import org.volminoduro.tools.RuneExtractor;
import org.volminoduro.tools.SWEXMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {if (i + 1 < args.length) {params.put(args[i], args[i + 1]);}}
        final String propertiesConfFilePath = params.get("-path");

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(propertiesConfFilePath);
        prop.load(fis);

        ObjectMapper objectMapper = new ObjectMapper();
        RuneExtractor.getInstance(objectMapper.readTree(new File(prop.getProperty("swex.path"))));
        SWEXMapper.initiateInstance(objectMapper.readTree(new File("src/main/resources/mapping.json")));
        FilterExtractor.getInstance(objectMapper.readTree(new File(prop.getProperty("filter.path"))));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(prop.getProperty("output.path")), RuneExtractor.extractAllRunes());
    }
}