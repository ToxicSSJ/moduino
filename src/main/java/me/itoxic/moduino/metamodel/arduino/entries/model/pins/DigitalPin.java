package me.itoxic.moduino.metamodel.arduino.entries.model.pins;

import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchDoubleVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;

public class DigitalPin extends Pin {

    public DigitalPin(String identifier) {
        super(identifier);
    }

    @Override
    public String getReadLiteral(SketchIntegerVariable variable) {
        return variable.getLabel() + " = " + "digitalRead(" + getLabel() + ");";
    }

    @Override
    public String getReadLiteral(SketchFloatVariable variable) {
        return variable.getLabel() + " = " + "digitalRead(" + getLabel() + ");";
    }

    @Override
    public String getReadLiteral(SketchDoubleVariable variable) {
        return variable.getLabel() + " = " + "analogRead(" + getLabel() + ");";
    }

    @Override
    public String getWriteLiteral(SketchIntegerVariable variable) {
        return "digitalWrite(" + getLabel() + ", " + variable.getDigitalForm() + ");";
    }

    @Override
    public String getWriteLiteral(SketchFloatVariable variable) {
        return "digitalWrite(" + getLabel() + ", " + variable.getLabel() + ");";
    }

    @Override
    public String getWriteLiteral(SketchDoubleVariable value) {
        return "analogWrite(" + getLabel() + ", " + value.getParametizedForm() + ");";
    }

}
