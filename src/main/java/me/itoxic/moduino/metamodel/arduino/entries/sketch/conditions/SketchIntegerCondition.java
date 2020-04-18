package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator.SketchIntegerComparatorType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;

public class SketchIntegerCondition extends SketchCondition {

    private SketchIntegerVariable first, second;
    private SketchDefineDirective<Integer> defSecond;
    private SketchFunctionCall firstCall, secondCall;

    private SketchInstruction instruction;
    private SketchFloatVariable sketchFloatVariable;

    private SketchIntegerComparatorType comparator;

    public SketchIntegerCondition(SketchInstruction instruction, SketchFloatVariable sketchFloatVariable, SketchIntegerComparatorType comparator) {

        this.instruction = instruction;
        this.sketchFloatVariable = sketchFloatVariable;

        this.comparator = comparator;

    }

    public SketchIntegerCondition(SketchFunctionCall firstCall, SketchFunctionCall secondCall, SketchIntegerComparatorType comparator) {

        this.firstCall = firstCall;
        this.secondCall = secondCall;
        this.comparator = comparator;

    }

    public SketchIntegerCondition(SketchIntegerVariable first, Integer second, SketchIntegerComparatorType comparator) {
        this(first, new SketchIntegerVariable(second), comparator);
    }

    public SketchIntegerCondition(SketchIntegerVariable first, SketchIntegerVariable second, SketchIntegerComparatorType comparator) {
        super();

        this.first = first;
        this.second = second;

        this.comparator = comparator;

    }

    public SketchIntegerCondition(SketchIntegerVariable first, SketchDefineDirective<Integer> defSecond, SketchIntegerComparatorType comparator) {
        super();

        this.first = first;
        this.defSecond = defSecond;

        this.comparator = comparator;

    }

    @Override
    public String getLiteralCondition() {

        if(instruction != null && sketchFloatVariable != null)
            return comparator.processComparation(instruction, sketchFloatVariable);

        if(firstCall != null && secondCall != null)
            return comparator.processComparation(firstCall, secondCall);

        if(defSecond != null)
            return comparator.processComparation(first, defSecond);

        return comparator.processComparation(first, second);

    }

    public SketchIntegerComparatorType getComparator() {
        return comparator;
    }

}
