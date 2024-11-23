package org.example;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.tools.Extractor;
import org.example.translated.rune.Rune;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {

        Collection<Rune> runesSelected = Extractor.extract("src/main/resources/Volminoduro-1858666.json", "");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("src/main/resources/runesSelected.json"), runesSelected);
    }
}