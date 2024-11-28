package org.volminoduro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.volminoduro.tools.Extractor;
import org.volminoduro.tools.Mapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        // TODO : Conf file to get all paths needed ?If not input when running main, get it from root folder
        final String mappingFilePath = Arrays.stream(args).filter(s -> s.equalsIgnoreCase("mapping_file_path")).findFirst().get();
        final String swexFilePath = Arrays.stream(args).filter(s -> s.equalsIgnoreCase("swex_file_path")).findFirst().get();
        final String filterFilePath = Arrays.stream(args).filter(s -> s.equalsIgnoreCase("filter_file_path")).findFirst().get();
        final String outputFilePath = "src/main/resources/runesSelected.json";
        // String outputFilePath = Arrays.stream(args).filter(s -> s.equalsIgnoreCase("swex_file_path")).findFirst().get();

        ObjectMapper objectMapper = new ObjectMapper();

        Extractor.getInstance(objectMapper.readTree(new File(swexFilePath)));

        Mapper.initiateInstance(objectMapper.readTree(new File(mappingFilePath)));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(outputFilePath), Extractor.extractAllRunes());
    }
}