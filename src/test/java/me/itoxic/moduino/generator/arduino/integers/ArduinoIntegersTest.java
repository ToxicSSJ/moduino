package me.itoxic.moduino.generator.arduino.integers;

import me.itoxic.moduino.MainTest;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.classes.Project;
import me.itoxic.moduino.metamodel.arduino.classes.model.pins.AnalogPin;
import me.itoxic.moduino.metamodel.arduino.classes.model.pins.DigitalPin;
import me.itoxic.moduino.metamodel.arduino.classes.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.conditions.SketchIntegerCondition;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.conditions.comparator.SketchIntegerComparatorType;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.operations.*;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.preloads.SketchNativeFunctionType;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.variables.operator.SketchNumberOperator;
import me.itoxic.moduino.metamodel.arduino.classes.type.PinMode;
import me.itoxic.moduino.metamodel.arduino.classes.sketch.operations.*;
import org.junit.Test;

import java.net.UnknownHostException;

public class ArduinoIntegersTest {

    @Test
    public void randomState() throws UnknownHostException {

        Project project = MainTest.buildTest("Led With Random States (medium)"); // Definir el proyecto

        ArduinoUnoBoard board = new ArduinoUnoBoard(); // Crear una board donde se montará todo el proyecto
        Sketch sketch = board.getSketch(); // Obtener el Sketch generico de la board

        DigitalPin led1 = board.useDigitalPin("13", "led1", PinMode.OUTPUT); // Usar el pin analogo A0 para el color rojo
        DigitalPin led2 = board.useDigitalPin("12", "led2", PinMode.OUTPUT);  // Usar el pin analogo A1 para el color verde
        DigitalPin led3 = board.useDigitalPin("8", "led3", PinMode.OUTPUT);  // Usar el pin analogo A2 para el color azul

        SketchFunction setup = sketch.getSetupFunction(); // Obtener la función setup generica
        SketchFunction loop = sketch.getLoopFunction(); // Obtener la función loop generica

        SketchIntegerVariable localState = new SketchIntegerVariable("localState", 0); // Crear una variable llamada red
        sketch.addVariable(localState);

        SketchSwitchOperation stateSwitch = new SketchSwitchOperation(localState);

        SketchCaseOperation op1 = new SketchCaseOperation(new SketchIntegerVariable(0));
        SketchCaseOperation op2 = new SketchCaseOperation(new SketchIntegerVariable(1));
        SketchCaseOperation op3 = new SketchCaseOperation(new SketchIntegerVariable(2));

        op1.addInstructions(
                led1.getWriteInstruction(1),
                led2.getWriteInstruction(0),
                led3.getWriteInstruction(0));

        op2.addInstructions(
                led1.getWriteInstruction(0),
                led2.getWriteInstruction(1),
                led3.getWriteInstruction(0));

        op3.addInstructions(
                led1.getWriteInstruction(0),
                led2.getWriteInstruction(0),
                led3.getWriteInstruction(1));

        stateSwitch.addCaseOperation(op1);
        stateSwitch.addCaseOperation(op2);
        stateSwitch.addCaseOperation(op3);

        SketchFunctionCall delay = new SketchFunctionCall(SketchNativeFunctionType.DELAY, new SketchIntegerVariable(500)); // Definir el uso de la función nativa #delay(int)
        SketchFunctionCall randomSeed = new SketchFunctionCall(SketchNativeFunctionType.RANDOMSEED); // Definir el uso de la función nativa #randomSeed(int)

        SketchFunctionCall randomState = new SketchFunctionCall(SketchNativeFunctionType.RANDOM); // Definir el uso para la variable localState de la función nativa #random(int, int)
        randomState.saveResult(localState);

        randomSeed.addParameters(new SketchIntegerVariable(255)); // Añadir el parametro de variable temporal 255 a la llamada de la función #randomSeed(int)
        randomState.addParameters(new SketchIntegerVariable(0), new SketchIntegerVariable(3)); // Añadir los parametros de variables temporales 1 y 255 a la función #random(int, int)

        setup.addInstructions(randomSeed); // Añadir la instrucción randomSeed a la función generica #setup() del Sketch

        loop.addInstructions(
                stateSwitch,
                randomState,
                delay); //

        project.addBoard(board); // Se añade la board al proyecto

        CodeBuffer buffer = board.generateCode(); // Se genera el codigo de la board
        buffer.printAll(); // Se imrpime en pantalla

    }

    @Test
    public void counterButton() throws UnknownHostException {

        Project project = MainTest.buildTest("7 Segment Counter (hard)"); // Definir el proyecto

        ArduinoUnoBoard board = new ArduinoUnoBoard(); // Crear una board donde se montará todo el proyecto
        Sketch sketch = board.getSketch(); // Obtener el Sketch generico de la board

        // A continuación se definen los pines digitales (4)
        // que serán utilizados para el decodificador de 7
        // segmentos. Esto permitirá convertir un numero
        // dado en binario a 8 señales digitales que
        // funcionaran para encender el led de 7 segmentos
        // con el valor elegido.
        //
        // Ejemplos:
        //
        // 1000 -> 8
        // 1001 -> 9
        // 0001 -> 1
        //

        DigitalPin binary1 = board.useDigitalPin("13", "binary1", PinMode.OUTPUT); // Pin que determina el primer bit (1000)
        DigitalPin binary2 = board.useDigitalPin("12", "binary2", PinMode.OUTPUT); // Pin que determina el segundo bit (0100)
        DigitalPin binary3 = board.useDigitalPin("8", "binary3", PinMode.OUTPUT); // Pin que determina el tercer bit (0010)
        DigitalPin binary4 = board.useDigitalPin("7", "binary4", PinMode.OUTPUT); // Pin que determina el cuarto bit (0010)

        DigitalPin button = board.useDigitalPin("4", "button", PinMode.INPUT); // Pin para conocer el estado del botón

        SketchIntegerVariable buttonStatus = new SketchIntegerVariable("buttonStatus"); // Variable para conocer el estado actual del botón
        SketchIntegerVariable currentNumber = new SketchIntegerVariable("currentNumber", 0); // Variable para conocer el numero actual

        SketchFunction f1 = sketch.getLoopFunction();
        sketch.addVariables(buttonStatus, currentNumber);

        SketchIntegerCondition condition1 = new SketchIntegerCondition(buttonStatus, 1, SketchIntegerComparatorType.EQUALS);
        SketchIntegerCondition condition2 = new SketchIntegerCondition(currentNumber, 10, SketchIntegerComparatorType.GREATER_OR_EQUAL);

        SketchIfOperation operation1 = new SketchIfOperation(condition1);

        SketchIfOperation operation2 = new SketchIfOperation(condition2);
        SketchElseOperation operation3 = new SketchElseOperation(operation2);

        SketchFunctionCall call = new SketchFunctionCall(SketchNativeFunctionType.DELAY, new SketchIntegerVariable(500));

        operation1.addInstructions(operation2);

        operation2.addInstruction(currentNumber.redefineVariable(0));
        operation3.addInstructions(currentNumber.operateVariable(null, SketchNumberOperator.SELF_ADD, null));

        f1.addInstructions(button.getReadInstruction(buttonStatus), operation1, call);

        SketchIfOperation number0 = new SketchIfOperation(new SketchIntegerCondition(currentNumber, 0, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number1 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 1, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number2 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 2, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number3 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 3, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number4 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 4, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number5 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 5, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number6 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 6, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number7 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 7, SketchIntegerComparatorType.EQUALS));
        SketchElseIfOperation number8 = new SketchElseIfOperation(number0, new SketchIntegerCondition(currentNumber, 8, SketchIntegerComparatorType.EQUALS));
        SketchElseOperation number9 = new SketchElseOperation(number0);

        number0.addInstructions( // Produce una señal (0000) igual a 0
                binary1.getWriteInstruction(0),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(0)
        );

        number1.addInstructions(
                binary1.getWriteInstruction(1),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(0)
        );

        number2.addInstructions(
                binary1.getWriteInstruction(0),
                binary2.getWriteInstruction(1),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(0)
        );

        number3.addInstructions(
                binary1.getWriteInstruction(1),
                binary2.getWriteInstruction(1),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(0)
        );

        number4.addInstructions(
                binary1.getWriteInstruction(0),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(1),
                binary4.getWriteInstruction(0)
        );

        number5.addInstructions(
                binary1.getWriteInstruction(1),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(1),
                binary4.getWriteInstruction(0)
        );

        number6.addInstructions(
                binary1.getWriteInstruction(0),
                binary2.getWriteInstruction(1),
                binary3.getWriteInstruction(1),
                binary4.getWriteInstruction(0)
        );

        number7.addInstructions(
                binary1.getWriteInstruction(1),
                binary2.getWriteInstruction(1),
                binary3.getWriteInstruction(1),
                binary4.getWriteInstruction(0)
        );

        number8.addInstructions(
                binary1.getWriteInstruction(0),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(1)
        );

        number9.addInstructions(
                binary1.getWriteInstruction(1),
                binary2.getWriteInstruction(0),
                binary3.getWriteInstruction(0),
                binary4.getWriteInstruction(1)
        );

        f1.addInstructions(number0);

        project.addBoard(board);

        CodeBuffer buffer = board.generateCode();
        buffer.printAll();

    }

    @Test
    public void ledRgbWithRandomIntegers() throws UnknownHostException {

        Project project = MainTest.buildTest("Led RGB With Random Integers (medium)"); // Definir el proyecto

        ArduinoUnoBoard board = new ArduinoUnoBoard(); // Crear una board donde se montará todo el proyecto
        Sketch sketch = board.getSketch(); // Obtener el Sketch generico de la board

        AnalogPin redPin = board.useAnalogPin("A0", "redPin", PinMode.OUTPUT); // Usar el pin analogo A0 para el color rojo
        AnalogPin greenPin = board.useAnalogPin("A1", "greenPin", PinMode.OUTPUT);  // Usar el pin analogo A1 para el color verde
        AnalogPin bluePin = board.useAnalogPin("A2", "bluePin", PinMode.OUTPUT);  // Usar el pin analogo A2 para el color azul

        SketchFunction setup = sketch.getSetupFunction(); // Obtener la función setup generica
        SketchFunction loop = sketch.getLoopFunction(); // Obtener la función loop generica

        SketchIntegerVariable red = new SketchIntegerVariable("red"); // Crear una variable llamada red
        SketchIntegerVariable green = new SketchIntegerVariable("green"); // Crear una variable llamada green
        SketchIntegerVariable blue = new SketchIntegerVariable("blue"); // Crear una variable llamada blue

        SketchFunctionCall delay = new SketchFunctionCall(SketchNativeFunctionType.DELAY, new SketchIntegerVariable(1)); // Definir el uso de la función nativa #delay(int)
        SketchFunctionCall randomSeed = new SketchFunctionCall(SketchNativeFunctionType.RANDOMSEED); // Definir el uso de la función nativa #randomSeed(int)

        SketchFunctionCall randomRed = new SketchFunctionCall(SketchNativeFunctionType.RANDOM); // Definir el uso para la variable red de la función nativa #random(int, int)
        SketchFunctionCall randomGreen = new SketchFunctionCall(SketchNativeFunctionType.RANDOM); // Definir el uso para la variable green de la función nativa #random(int, int)
        SketchFunctionCall randomBlue = new SketchFunctionCall(SketchNativeFunctionType.RANDOM); // Definir el uso para la variable blue de la función nativa #random(int, int)

        randomRed.saveResult(red); // Guardar el resultado de la llamada a #random(int, int) en la variable red
        randomGreen.saveResult(green); // Guardar el resultado de la llamada a #random(int, int) en la variable green
        randomBlue.saveResult(blue); // Guardar el resultado de la llamada a #random(int, int) en la variable blue

        SketchIntegerVariable one = new SketchIntegerVariable(1); // Definir una variable temporal con valor (1)
        SketchIntegerVariable twoHundredFiftyFive = new SketchIntegerVariable(255); // Definir una variable temporal con valor (255)

        randomSeed.addParameters(twoHundredFiftyFive); // Añadir el parametro de variable temporal 255 a la llamada de la función #randomSeed(int)

        randomRed.addParameters(one, twoHundredFiftyFive); // Añadir los parametros de variables temporales 1 y 255 a la función #random(int, int)
        randomGreen.addParameters(one, twoHundredFiftyFive); // Añadir los parametros de variables temporales 1 y 255 a la función #random(int, int)
        randomBlue.addParameters(one, twoHundredFiftyFive); // Añadir los parametros de variables temporales 1 y 255 a la función #random(int, int)

        sketch.addVariables(red, blue, green); // Añadir las variables rojo, azul y verde previamente definidas al contexto general del Sketch

        setup.addInstructions(randomSeed); // Añadir la instrucción randomSeed a la función generica #setup() del Sketch
        loop.addInstructions(randomRed, randomGreen, randomBlue); // Añadir las instrucciones randomRed, randomGreen y RandomBlue a la función generica #loop() del Sketch

        loop.addInstructions(
                redPin.getWriteInstruction(red),
                greenPin.getWriteInstruction(green),
                bluePin.getWriteInstruction(blue),
                delay); // Añadir las funciones necesarias para escribir el color random en cada entrada del led RGB, además se añade el delay de 50 mili-segundos previamente definido

        project.addBoard(board); // Se añade la board al proyecto

        CodeBuffer buffer = board.generateCode(); // Se genera el codigo de la board
        buffer.printAll(); // Se imprime en pantalla

    }

}
