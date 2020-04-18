package me.itoxic.moduino;

import me.itoxic.moduino.generator.arduino.booleans.ArduinoBooleansTest;
import me.itoxic.moduino.generator.arduino.integers.ArduinoIntegersTest;
import me.itoxic.moduino.metamodel.arduino.ArduinoMetamodelManager;
import me.itoxic.moduino.metamodel.arduino.classes.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.fail;

@RunWith(Suite.class)
@Suite.SuiteClasses({

    ArduinoBooleansTest.class,
    ArduinoIntegersTest.class

})
public class MainTest {

    @Test
    public void testAll() {

    }

    public static void main(String[] args) {

        

    }

    public static Project buildTest(String testName) {

        ArduinoMetamodelManager manager = new ArduinoMetamodelManager();
        Project project = new Project(testName, manager);

        return project;

    }

}
