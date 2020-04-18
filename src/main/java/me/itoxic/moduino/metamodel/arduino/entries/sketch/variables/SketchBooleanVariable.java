package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchContext;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchBooleanOperator;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchBooleanVariable extends SketchVariable<Boolean, SketchBooleanOperator> {

    public SketchBooleanVariable(Boolean value) {
        super(value, "boolean", false, false);
    }

    public SketchBooleanVariable(String label) {
        super(label, null, "boolean", false, false);
    }

    public SketchBooleanVariable(String label, Boolean value) {
        this(label, value, false, false);
    }

    public SketchBooleanVariable(String label, boolean parameter, boolean constant) {
        super(label, null, "boolean", parameter, constant);
    }

    public SketchBooleanVariable(String label, Boolean value, boolean parameter, boolean constant) {
        super(label, value, "boolean", parameter, constant);
    }

    @Override
    public SketchInstruction redefineVariable(final Boolean value) {

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

                if(value == null)
                    buffer.appendLine(getLabel() + " = " + "null;");
                else
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
    public SketchInstruction operateVariable(final Boolean value, final SketchBooleanOperator operator, final Object[] args) {
        return new SketchInstruction() {

            @Override
            public boolean appendCodeLiteral(CodeBuffer buffer) {
                return false;
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
