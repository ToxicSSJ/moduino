package me.itoxic.moduino.metamodel.arduino;

import me.itoxic.moduino.metamodel.Metamodel;

public class ArduinoMetamodel extends Metamodel {

    public ArduinoMetamodel() {
        super("arduino", "1.0", new ArduinoMetamodelManager());
    }

}
