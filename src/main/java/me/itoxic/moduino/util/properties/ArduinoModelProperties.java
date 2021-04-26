package me.itoxic.moduino.util.properties;

import java.util.List;

public class ArduinoModelProperties {

    private String[] keywords;

    private String[] digital_pins;
    private String[] analog_pins;
    private String[] pwm_pins;

    public ArduinoModelProperties(List<String> keywords, List<String> digital, List<String> analog, List<String> pwm) {
        this.keywords = keywords.toArray(new String[keywords.size()]);
        this.digital_pins = digital.toArray(new String[digital.size()]);
        this.analog_pins = analog.toArray(new String[analog.size()]);
        this.pwm_pins = pwm.toArray(new String[pwm.size()]);
    }

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
