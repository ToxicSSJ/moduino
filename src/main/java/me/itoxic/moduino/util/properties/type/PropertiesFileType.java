package me.itoxic.moduino.util.properties.type;

import me.itoxic.moduino.util.properties.ArduinoModelProperties;
import me.itoxic.moduino.util.properties.PropertiesHelper;

public enum PropertiesFileType {

    ARDUINO_UNO("model/arduino_uno"),
    ARDUINO_LEONARDO("model/arduino_leonardo"),
    ARDUINO_MEGA("model/arduino_mega"),

    C_LANGUAGE_INFO("code/c_lang");

    String path;

    PropertiesFileType(String path) {

        this.path = path;

    }

    public String getPath() {
        return path;
    }

    public String[] getCustom() {
        return PropertiesHelper.openFile(this).getKeywords();
    }

    public ArduinoModelProperties open() {
        return PropertiesHelper.openFile(this);
    }

}
