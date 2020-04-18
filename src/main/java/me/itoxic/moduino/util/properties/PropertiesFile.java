package me.itoxic.moduino.util.properties;

import me.itoxic.moduino.util.properties.type.PropertiesFileType;

public class PropertiesFile {

    private ArduinoModelProperties properties;
    private PropertiesFileType type;

    public PropertiesFile(ArduinoModelProperties properties, PropertiesFileType type) {

        this.properties = properties;
        this.type = type;

    }

    public ArduinoModelProperties getProperties() {
        return properties;
    }

    public PropertiesFileType getType() {
        return type;
    }

}
