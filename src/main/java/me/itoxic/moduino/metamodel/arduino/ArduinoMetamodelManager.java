package me.itoxic.moduino.metamodel.arduino;

import me.itoxic.moduino.metamodel.MetamodelManager;
import me.itoxic.moduino.metamodel.arduino.entries.Project;

public class ArduinoMetamodelManager extends MetamodelManager {

    public Project create(String projectName) {
        return new Project(projectName, this);
    }

}
