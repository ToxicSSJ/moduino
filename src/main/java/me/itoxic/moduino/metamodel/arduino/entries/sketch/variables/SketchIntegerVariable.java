package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchContext;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchNumberOperator;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchIntegerVariable extends SketchVariable<Integer, SketchNumberOperator> {

    public SketchIntegerVariable(String label) {
        super(label, null, "int", false, false);
    }

    public SketchIntegerVariable(Integer value) {
        super(value, "int", false, false);
    }

    public SketchIntegerVariable(String label, Integer value) {
        super(label, value, "int", false, false);
    }

    public SketchIntegerVariable(String label, boolean parameter, boolean constant) {
        super(label, null, "int", parameter, constant);
    }

    public SketchIntegerVariable(String label, Integer value, boolean parameter, boolean constant) {
        super(label, value, "int", parameter, constant);
    }

    public SketchIntegerVariable(String label, Integer value, boolean constant) {
        super(label, value, "int", false, constant);
    }

    public void setUnsigned(boolean unsigned) {

        if(unsigned)
            super.setKeyword("unsigned int");
        else
            super.setKeyword("int");

    }

    public SketchInstruction redefineVariable(final SketchDefineDirective<Integer> other) {

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

                buffer.appendLine(getLabel() + " = " + other.getLabel() + ";");
                return true;

            }

            @Override
            public SketchContext.InstructionType getType() {
                return SketchContext.InstructionType.INSTRUCTION_VARIABLE_USED;
            }

        };

    }

    public SketchInstruction redefineVariable(final SketchIntegerVariable other) {

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

                buffer.appendLine(getLabel() + " = " + other.getLabel() + ";");
                return true;

            }

            @Override
            public SketchContext.InstructionType getType() {
                return SketchContext.InstructionType.INSTRUCTION_VARIABLE_USED;
            }

        };

    }

    @Override
    public SketchInstruction redefineVariable(final Integer value) {

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
    public SketchInstruction operateVariable(final Integer value, final SketchNumberOperator operator, final Object[] args) {
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

    public String getDigitalForm() {

        if(getLabel() != null)
            return getLabel();

        if((Integer) getValue() == 0)
            return "LOW";

        return "HIGH";

    }

    @Override
    public SketchContext.InstructionType getType() {
        return SketchContext.InstructionType.INSTRUCTION_SKETCH_OTHER;
    }

}
