package me.itoxic.moduino.metamodel.arduino.restrictions;

import me.itoxic.moduino.util.properties.type.PropertiesFileType;

public class ArduinoRestrictions {

    public static final String[] KEYWORDS = PropertiesFileType.C_LANGUAGE_INFO.open().getKeywords();

    public static final String VARIABLE_BASIC_PATTERN = "^\\d+.*|.*\\s+.*";
    public static final String VARIABLE_SPECIAL_PATTERN = "^[!@#$%^&*].*";

}
