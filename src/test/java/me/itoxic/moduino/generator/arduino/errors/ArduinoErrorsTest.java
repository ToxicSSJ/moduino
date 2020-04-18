package me.itoxic.moduino.generator.arduino.errors;

import me.itoxic.moduino.MainTest;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.classes.Project;
import me.itoxic.moduino.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.variables.SketchBooleanVariable;
import org.junit.Test;

import java.net.UnknownHostException;

public class ArduinoErrorsTest {

    @Test
    public void contextError() throws UnknownHostException {

        Project project = MainTest.buildTest("Led With Random States (medium)"); // Definir el proyecto

        ArduinoUnoBoard board = new ArduinoUnoBoard(); // Crear una board donde se montará todo el proyecto
        Sketch sketch = board.getSketch(); // Obtener el Sketch generico de la board

        SketchFunction setup = sketch.getSetupFunction(); // Obtener la función setup generica
        SketchFunction loop = sketch.getLoopFunction(); // Obtener la función loop generica

        SketchBooleanVariable variable = new SketchBooleanVariable("test", true);

        setup.addInstructions(variable.redefineVariable(false), variable); // Añadir la instrucción randomSeed a la función generica #setup() del Sketch

        project.addBoard(board); // Se añade la board al proyecto

        CodeBuffer buffer = board.generateCode(); // Se genera el codigo de la board
        buffer.printAll(); // Se imprime en pantalla

    }

}
