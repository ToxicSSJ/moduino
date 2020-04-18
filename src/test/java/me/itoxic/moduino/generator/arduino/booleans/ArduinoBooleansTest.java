package me.itoxic.moduino.generator.arduino.booleans;

import me.itoxic.moduino.MainTest;
import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.Project;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.DigitalPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.uno.ArduinoUnoBoard;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.SketchBooleanCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator.SketchBooleanComparatorType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchElseIfOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchElseOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.operations.SketchIfOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preloads.SketchNativeFunctionType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchBooleanVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.entries.type.PinMode;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;
import org.junit.Test;

import java.net.UnknownHostException;

public class ArduinoBooleansTest {

    @Test(expected = ArduinoRestrictionException.class)
    public void testingBooleans1() {

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        Sketch sketch = board.getSketch();

        sketch.addVariable(new SketchBooleanVariable("led1", false));
        sketch.addVariable(new SketchBooleanVariable("led1", false));

    }

    @Test(expected = ArduinoRestrictionException.class)
    public void testingBooleans2() {

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        Sketch sketch = board.getSketch();

        sketch.addVariable(new SketchBooleanVariable("led1", false));
        sketch.addVariable(new SketchBooleanVariable("led1", true));

    }

    @Test
    public void testingBooleans3() throws UnknownHostException {

        Project project = MainTest.buildTest("Testing Booleans #3");

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        Sketch sketch = board.getSketch();

        sketch.addVariable(new SketchBooleanVariable("led_1", false));
        sketch.addVariable(new SketchBooleanVariable("led_2", false));

        project.addBoard(board);

        CodeBuffer buffer = board.generateCode();
        buffer.printAll();

    }

    @Test
    public void ledsWithBooleans() throws UnknownHostException {

        Project project = MainTest.buildTest("Leds With Booleans (basic)");

        ArduinoUnoBoard board = new ArduinoUnoBoard();
        Sketch sketch = board.getSketch();

        DigitalPin pin1 = board.useDigitalPin("13", "led1", PinMode.OUTPUT);
        DigitalPin pin2 = board.useDigitalPin("8", "led2", PinMode.OUTPUT);
        DigitalPin pin3 = board.useDigitalPin("2", "led3", PinMode.OUTPUT);

        SketchBooleanVariable led1 = new SketchBooleanVariable("led1_enabled", false);
        SketchBooleanVariable led2 = new SketchBooleanVariable("led2_enabled", false);
        SketchBooleanVariable led3 = new SketchBooleanVariable("led3_enabled", false);

        SketchFunction f1 = sketch.getLoopFunction();
        sketch.addVariables(led1, led2, led3);

        SketchBooleanCondition condition1 = new SketchBooleanCondition(led1, true, SketchBooleanComparatorType.EQUALS);
        SketchBooleanCondition condition2 = new SketchBooleanCondition(led2, true, SketchBooleanComparatorType.EQUALS);

        SketchIfOperation operation1 = new SketchIfOperation(condition1);
        SketchElseIfOperation operation2 = new SketchElseIfOperation(operation1, condition2);
        SketchElseOperation operation3 = new SketchElseOperation(operation1);

        SketchFunctionCall call = new SketchFunctionCall(SketchNativeFunctionType.DELAY, new SketchIntegerVariable(1000));

        operation1.addInstructions(
                pin1.getWriteInstruction(1),
                pin2.getWriteInstruction(0),
                pin3.getWriteInstruction(0),
                led1.redefineVariable(false),
                led2.redefineVariable(true));

        operation2.addInstructions(
                pin1.getWriteInstruction(0),
                pin2.getWriteInstruction(1),
                pin3.getWriteInstruction(0),
                led2.redefineVariable(false),
                led3.redefineVariable(true));

        operation3.addInstructions(
                pin1.getWriteInstruction(0),
                pin2.getWriteInstruction(0),
                pin3.getWriteInstruction(1),
                led3.redefineVariable(false),
                led1.redefineVariable(true));


        f1.addInstructions(operation1, call);

        project.addBoard(board);

        CodeBuffer buffer = board.generateCode();
        buffer.printAll();

    }

}
