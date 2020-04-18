package me.itoxic.moduino.metamodel.arduino.entries.model.uno;

import me.itoxic.moduino.generator.type.arduino.references.ArduinoModelType;
import me.itoxic.moduino.metamodel.arduino.entries.Board;
import java.util.UUID;

public class ArduinoUnoBoard extends Board {

    public ArduinoUnoBoard() {
        this(UUID.randomUUID().toString().substring(1, 5), UUID.randomUUID().toString().substring(1, 5));
    }

    public ArduinoUnoBoard(String moduleName, String fileName) {
        super(moduleName, fileName, ArduinoModelType.ARDUINO_UNO);
    }

}
