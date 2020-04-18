package me.itoxic.moduino;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.ArduinoMetamodel;
import me.itoxic.moduino.metamodel.arduino.ArduinoMetamodelManager;
import me.itoxic.moduino.metamodel.arduino.entries.Project;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchStringVariable;

import java.io.IOException;

/**
 * Esta clase se encarga de la inicialización
 * general del programa, actualmente permite
 * dicho inicio con diferentes argumentos.
 *
 * @version 1.0
 * @since   2019-07-26
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // Pre-creación
        // Instanciar clases fundamentales

        ArduinoMetamodel metamodel = new ArduinoMetamodel(); // Se instancia la base del meta-modelo
        ArduinoMetamodelManager manager = (ArduinoMetamodelManager) metamodel.getManager(); // Se crea un manager del mismo meta-modelo

        Project project = manager.create("Semaphore"); // Se crea un nuevo Proyecto
        ArduinoUnoBoard board = new ArduinoUnoBoard("ControladorDeTrafico", "Semaforo"); // Creamos una placa (en este caso usaremos ArduinoUNO)
        Sketch sketch = board.getSketch(); // Obtenemos el Sketch que viene integrado en la placa (board)

        project.addBoard(board); // Le añadimos la placa que hemos creado al proyecto

        // Adición de instrucciones

        sketch.addVariable(new SketchIntegerVariable("test", 2, true)); // Añadimos una variable (en el contexto de la base del Sketch)
        sketch.addVariable(new SketchIntegerVariable("abc", 3, true)); // Añadimos otra variable  (en el contexto de la base del Sketch)

        SketchFunction function = sketch.getSetupFunction(); // Obtenemos la función setup del Sketch

        function.addInstruction(new SketchStringVariable("name", "Carlos", true)); // Le añadimos una variable (en el contexto de la función)
        function.addInstruction(new SketchIntegerVariable("age", 23, false));  // Le añadimos una variable (en el contexto de la función)

        CodeBuffer buffer = board.generateCode(); // Generamos el codigo
        buffer.printAll(); // Lo imprimimos en pantalla

    }

}
