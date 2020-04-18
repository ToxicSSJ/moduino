package me.itoxic.moduino.generator.type.arduino;

import me.itoxic.moduino.generator.type.arduino.references.ArduinoModelType;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.AnalogPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.DigitalPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.PWMPin;

import java.util.LinkedList;

public class ArduinoPinsGenerator {

    public static void buildDigital(LinkedList<DigitalPin> pins, ArduinoModelType modelType) {

        String[] identifiers = modelType.getDigitalIdentifiers();

        for(String identifier : identifiers)
            pins.add(new DigitalPin(identifier));

    }

    public static void buildAnalog(LinkedList<AnalogPin> pins, ArduinoModelType modelType) {

        String[] identifiers = modelType.getAnalogIndentifiers();

        for(String identifier : identifiers)
            pins.add(new AnalogPin(identifier));

    }

    public static void buildPWM(LinkedList<PWMPin> pins, ArduinoModelType modelType) {

        String[] identifiers = modelType.getPWMIdentifiers();

        for(String identifier : identifiers)
            pins.add(new PWMPin(identifier));

    }

}
