package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchContext;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchNumberOperator;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchDoubleVariable extends SketchVariable<Double, SketchNumberOperator> {

    public SketchDoubleVariable(String label) {
        super(label, null, "double", false, false);
    }

    public SketchDoubleVariable(Double value) {
        super(value, "double", false, false);
    }

    public SketchDoubleVariable(String label, Double value) {
        super(label, value, "double", false, false);
    }

    public SketchDoubleVariable(String label, boolean parameter, boolean constant) {
        super(label, null, "double", parameter, constant);
    }

    public SketchDoubleVariable(String label, Double value, boolean constant) {
        super(label, value, "double", false, constant);
    }

    public void setUnsigned(boolean unsigned) {

        if(unsigned)
            super.setKeyword("unsigned double");
        else
            super.setKeyword("double");

    }

    @Override
    public SketchInstruction redefineVariable(final Double value) {

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
    public SketchInstruction operateVariable(final Double value, final SketchNumberOperator operator, final Object[] args) {
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
