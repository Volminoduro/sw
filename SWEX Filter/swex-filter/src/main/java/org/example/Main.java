package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tools.Extractor;
import org.example.translated.Rune;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {

        Collection<Rune> runesSelected = Extractor.extract("src/main/resources/Volminoduro-1858666.json", "");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/main/resources/runesSelected.json"), runesSelected);
    }
}