package me.itoxic.moduino.metamodel.arduino.entries.model.pins;

import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchDoubleVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;

public class AnalogPin extends Pin {

    public AnalogPin(String identifier) {
        super(identifier);
    }

    @Override
    public String getReadLiteral(SketchIntegerVariable variable) {
        return variable.getLabel() + " = " + "analogRead(" + getLabel() + ");";
    }

    @Override
    public String getReadLiteral(SketchFloatVariable variable) {
        return variable.getLabel() + " = " + "analogRead(" + getLabel() + ");";
    }

    @Override
    public String getReadLiteral(SketchDoubleVariable variable) {
        return variable.getLabel() + " = " + "analogRead(" + getLabel() + ");";
    }

    @Override
    public String getWriteLiteral(SketchIntegerVariable value) {
        return "analogWrite(" + getLabel() + ", " + value.getParametizedForm() + ");";
    }

    @Override
    public String getWriteLiteral(SketchFloatVariable value) {
        return "analogWrite(" + getLabel() + ", " + value.getParametizedForm() + ");";
    }

    @Override
    public String getWriteLiteral(SketchDoubleVariable value) {
        return "analogWrite(" + getLabel() + ", " + value.getParametizedForm() + ");";
    }

}
