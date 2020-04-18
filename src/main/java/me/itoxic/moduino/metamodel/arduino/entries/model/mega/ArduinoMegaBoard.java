package me.itoxic.moduino.metamodel.arduino.entries.model.mega;

import me.itoxic.moduino.generator.type.arduino.references.ArduinoModelType;
import me.itoxic.moduino.metamodel.arduino.entries.Board;

import java.util.UUID;

public class ArduinoMegaBoard extends Board {

    public ArduinoMegaBoard() {
        this(UUID.randomUUID().toString().substring(1, 5), UUID.randomUUID().toString().substring(1, 5));
    }

    public ArduinoMegaBoard(String moduleName, String fileName) {
        super(moduleName, fileName, ArduinoModelType.ARDUINO_MEGA);
    }

}
