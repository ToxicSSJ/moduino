package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchContext;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.operator.SketchStringOperator;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchStringVariable extends SketchVariable<String, SketchStringOperator> {

    public SketchStringVariable(String value) {
        super(value, "String", false, false);
    }

    public SketchStringVariable(String label, String value) {
        this(label, value, false);
    }

    public SketchStringVariable(String label, boolean constant) {
        this(label, null, constant);
    }

    public SketchStringVariable(String label, boolean parameter, boolean constant) {
        super(label, null, "String", parameter, constant);
    }

    public SketchStringVariable(String label, String value, boolean constant) {
        super(label, value, "String", false, constant);
    }

    @Override
    public SketchInstruction redefineVariable(final String value) {

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

                buffer.appendLine(getLabel() + " = " + "\"" + value + "\";");
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
        return "\"" + getValue().toString() + "\"";
    }

    @Override
    public String getParametizedForm() {

        if(getLabel() == null)
            return getLiteralValue();

        return getLabel();

    }

    @Override
    public SketchInstruction operateVariable(final String value, final SketchStringOperator operator, final Object[] args) {
        return new SketchInstruction() {

            @Override
            public boolean appendCodeLiteral(CodeBuffer buffer) {

                switch(operator) {

                    case CONCAT:
                        buffer.appendLine(getLabel() + " = " + getLabel() + " + " + "\"" + value + "\";");
                        break;
                    case REPLACE:
                        buffer.appendLine(getLabel() + ".replace(\"" + value + "\", \"" + args[0].toString() + "\");");
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
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        if(isParameter()) {

            buffer.append((isConstant() ? "const " : "") + getKeyword() + " " + getLabel(), false);
            return true;

        }

        if(getLabel() == null) {

            buffer.appendLine("\"" + getValue().toString() + "\"");
            return true;

        }

        if(getValue() == null)
            buffer.appendLine((isConstant() ? "const " : "") + getKeyword() + " " + getLabel() + ";");
        else
            buffer.appendLine((isConstant() ? "const " : "") + getKeyword() + " " + getLabel() + " = " + "\"" + getValue() + "\";");

        return true;

    }

    @Override
    public SketchContext.InstructionType getType() {
        return SketchContext.InstructionType.INSTRUCTION_SKETCH_OTHER;
    }

}
