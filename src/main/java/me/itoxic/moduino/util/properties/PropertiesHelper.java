package me.itoxic.moduino.util.properties;

import me.itoxic.moduino.util.properties.type.PropertiesFileType;

import java.util.Arrays;
import java.util.List;

public class PropertiesHelper {

    // private static final String EXTENSION = ".json";

    private static final List<String> KEYWORDS = Arrays.asList(
            "auto",
            "break",
            "case",
            "char",
            "const",
            "continue",
            "default",
            "do",
            "double",
            "else",
            "enum",
            "extern",
            "float",
            "for",
            "goto",
            "if",
            "int",
            "long",
            "register",
            "return",
            "short",
            "signed",
            "sizeof",
            "static",
            "struct",
            "switch",
            "typedef",
            "union",
            "unsigned",
            "void",
            "volatile",
            "while",
            "boolean"
    );

    public static ArduinoModelProperties openFile(PropertiesFileType type) {
        return new ArduinoModelProperties(
                KEYWORDS,
                Arrays.asList("13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"),
                Arrays.asList("A0", "A1", "A2", "A3", "A4", "A5", "A6"),
                Arrays.asList("3", "5", "6", "9", "10", "11")
        );
    }

}
