package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchContext;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchNumberOperator;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchFloatVariable extends SketchVariable<Float, SketchNumberOperator> {

    public SketchFloatVariable(String label) {
        super(label, null, "float", false, false);
    }

    public SketchFloatVariable(Float value) {
        super(value, "float", false, false);
    }

    public SketchFloatVariable(String label, Float value) {
        super(label, value, "float", false, false);
    }

    public SketchFloatVariable(String label, boolean parameter, boolean constant) {
        super(label, null, "float", parameter, constant);
    }

    public SketchFloatVariable(String label, Float value, boolean constant) {
        super(label, value, "float", false, constant);
    }

    public void setUnsigned(boolean unsigned) {

        if(unsigned)
            super.setKeyword("unsigned float");
        else
            super.setKeyword("float");

    }

    public SketchInstruction redefineVariable(final Pin pin) {

        //
        // Restricciones de redefinición de variables
        //
        // Funciona para validar que no se redefina
        // una variable constante.
        //

        if(isConstant())
            throw new ArduinoRestrictionException("La variable '" + getLabel() + "' es una constante, por lo cual no puede ser redefinida.");

        // Fin de las restricciones

        SketchFloatVariable instance = this;

        return new SketchInstruction() {

            @Override
            public boolean appendCodeLiteral(CodeBuffer buffer) {

                buffer.appendLine(pin.getReadLiteral(instance));
                return true;

            }

            @Override
            public SketchContext.InstructionType getType() {
                return SketchContext.InstructionType.INSTRUCTION_VARIABLE_USED;
            }

        };

    }

    @Override
    public SketchInstruction redefineVariable(final Float value) {

        //
        // Restricciones de redefinición de variables
        //
        // Funciona para validar que no se redefina
        // una variable constante.
        //

        if(isConstant())
            throw new ArduinoRestrictionException("La variable '" + getLabel() + "' es una constante, por lo cual no puede ser redefinida.");

        // Fin de las restricciones

        return new SketchInstruction() {

            @Override
            public boolean appendCodeLiteral(CodeBuffer buffer) {

                buffer.appendLine(getLabel() + " = " + value.toString() + ";");
                return true;

            }

            @Override
            public SketchContext.InstructionType getType() {
                return SketchContext.InstructionType.INSTRUCTION_VARIABLE_USED;
            }

        };

    }

    @Override
    public SketchInstruction operateVariable(final Float value, final SketchNumberOperator operator, final Object[] args) {
        return new SketchInstruction() {

            @Override
            public boolean appendCodeLiteral(CodeBuffer buffer) {

                switch(operator) {

                    case SELF_ADD:
                        buffer.appendLine(getLabel() + "++;");
                        break;
                    case SELF_STIR:
                        buffer.appendLine(getLabel() + "--;");
                        break;
                    case COMMON_ADD:
                        buffer.appendLine(getLabel() + " += " + value + ";");
                        break;
                    case COMMON_STIR:
                        buffer.appendLine(getLabel() + " += " + value + ";");
                        break;

                }

                return true;

            }

            @Override
            public SketchContext.InstructionType getType() {
                return SketchContext.InstructionType.INSTRUCTION_VARIABLE_USED;
            }

        };
    }

    @Override
    public String getLiteralValue() {
        return getValue().toString();
    }

    @Override
    public String getParametizedForm() {

        if(getLabel() == null)
            return getLiteralValue();

        return getLabel();

    }

    @Override
    public SketchContext.InstructionType getType() {
        return SketchContext.InstructionType.INSTRUCTION_SKETCH_OTHER;
    }

}
