package me.itoxic.moduino.util.properties;

public class ArduinoModelProperties {

    private String[] keywords;

    private String[] digital_pins;
    private String[] analog_pins;
    private String[] pwm_pins;

    public String[] getKeywords() {
        return keywords;
    }

    public String[] getDigitalPins() {
        return digital_pins;
    }

    public String[] getAnalogPins() {
        return analog_pins;
    }

    public String[] getPWMPins() {
        return pwm_pins;
    }

}
