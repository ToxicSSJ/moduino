package me.itoxic.moduino.metamodel.arduino.entries.model;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchDoubleVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.entries.type.DigitalPinValue;
import me.itoxic.moduino.metamodel.arduino.entries.type.PinMode;

public abstract class Pin implements SketchInstruction {

    private String label;
    private String identifier;

    private boolean used;
    private PinMode mode;

    public Pin(String identifier) {

        this.identifier = identifier;

    }

    public void use(String label, PinMode mode) {

        this.label = label.toUpperCase();
        this.mode = mode;

        this.used = true;

    }

    public PinMode getPinMode() {
        return mode;
    }

    public String getLabel() {
        return label;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isUsed() {
        return used;
    }

    public SketchInstruction getReadInstruction(SketchIntegerVariable variable) {
        return buffer -> {
            buffer.appendLine(getReadLiteral(variable));
            return true;
        };
    }

    public SketchInstruction getReadInstruction(SketchDoubleVariable variable) {
        return buffer -> {
            buffer.appendLine(getReadLiteral(variable));
            return true;
        };
    }

    public SketchInstruction getWriteInstruction(Integer variable) {
        return getWriteInstruction(new SketchIntegerVariable(variable));
    }

    public SketchInstruction getWriteInstruction(SketchIntegerVariable variable) {
        return buffer -> {
            buffer.appendLine(getWriteLiteral(variable));
            return true;
        };
    }

    public SketchInstruction getWriteInstruction(SketchFloatVariable variable) {
        return buffer -> {
            buffer.appendLine(getWriteLiteral(variable));
            return true;
        };
    }

    public SketchInstruction getWriteInstruction(SketchDoubleVariable variable) {
        return buffer -> {
            buffer.appendLine(getWriteLiteral(variable));
            return true;
        };
    }

    // public abstract SketchInstruction readAndSave(SketchIntegerVariable variable);

    public abstract String getReadLiteral(SketchIntegerVariable variable);

    public abstract String getReadLiteral(SketchFloatVariable variable);

    public abstract String getReadLiteral(SketchDoubleVariable variable);

    public abstract String getWriteLiteral(SketchIntegerVariable value);

    public abstract String getWriteLiteral(SketchFloatVariable value);

    public abstract String getWriteLiteral(SketchDoubleVariable value);

    public void appendReadLiteral(CodeBuffer buffer, SketchIntegerVariable variable) {
        buffer.appendLine(getReadLiteral(variable));
    }

    public void appendWriteLiteral(CodeBuffer buffer, SketchDoubleVariable value) {
        buffer.appendLine(getWriteLiteral(value));
    }

    public void appendWriteLiteral(CodeBuffer buffer, SketchIntegerVariable value) {
        buffer.appendLine(getWriteLiteral(value));
    }

    public void appendWriteLiteral(CodeBuffer buffer, DigitalPinValue digitalPinValue) {
        appendWriteLiteral(buffer, new SketchIntegerVariable(digitalPinValue.getValue()));
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        if(used) {

            buffer.appendLine("#define " + label + " " + identifier);
            return true;

        }

        return false;

    }

}
