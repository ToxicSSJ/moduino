package me.itoxic.moduino.generator.type.arduino.references;

import me.itoxic.moduino.util.properties.ArduinoModelProperties;
import me.itoxic.moduino.util.properties.type.PropertiesFileType;

public enum ArduinoModelType {

    ARDUINO_UNO("Arduino Genuino UNO", PropertiesFileType.ARDUINO_UNO),
    // ARDUINO_LEONARDO("Arduino Leonardo", PropertiesFileType.ARDUINO_LEONARDO),
    ARDUINO_MEGA("Arduino Mega", PropertiesFileType.ARDUINO_MEGA),

    ;

    String modelName;
    ArduinoModelProperties modelProperties;

    ArduinoModelType(String modelName, PropertiesFileType propertiesFileType) {

        this.modelName = modelName;
        this.modelProperties = propertiesFileType.open();

    }

    public String getModelName() {
        return modelName;
    }

    private ArduinoModelProperties getProperties() {
        return modelProperties;
    }

    public String[] getDigitalIdentifiers() {
        return modelProperties.getDigitalPins();
    }

    public String[] getAnalogIndentifiers() {
        return modelProperties.getAnalogPins();
    }

    public String[] getPWMIdentifiers() {
        return modelProperties.getPWMPins();
    }

}
