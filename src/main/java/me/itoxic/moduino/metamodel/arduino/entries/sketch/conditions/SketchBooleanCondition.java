package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator.SketchBooleanComparatorType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.type.SketchFunctionType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchBooleanVariable;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchBooleanCondition extends SketchCondition {

    private SketchBooleanVariable first, second;
    private SketchFunctionCall firstCall, secondCall;
    private SketchBooleanComparatorType comparator;

    public SketchBooleanCondition(SketchFunctionCall firstCall) {

        if(firstCall.getSketchFunctionType() != SketchFunctionType.BOOLEAN)
            throw new ArduinoRestrictionException("El resultado no es un booleano.");

        this.firstCall = firstCall;

    }

    public SketchBooleanCondition(SketchBooleanVariable condition) {

        this.first = condition;

    }

    public SketchBooleanCondition(SketchBooleanVariable first, Boolean second, SketchBooleanComparatorType comparator) {
        this(first, new SketchBooleanVariable(second), comparator);
    }

    public SketchBooleanCondition(SketchBooleanVariable first, SketchBooleanVariable second, SketchBooleanComparatorType comparator) {
        super();

        this.first = first;
        this.second = second;

        this.comparator = comparator;

    }

    @Override
    public String getLiteralCondition() {

        if(firstCall != null)
            return firstCall.getFunctionLiteralCall(false);

        if(comparator == null)
            return first.getParametizedForm();

        return comparator.processComparation(first, second);
    }

    public SketchBooleanComparatorType getComparator() {
        return comparator;
    }

}
