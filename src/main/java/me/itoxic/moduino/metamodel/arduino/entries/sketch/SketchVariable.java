package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.restrictions.ArduinoRestrictions;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.LinkedList;

public abstract class SketchVariable<T, E> implements SketchInstruction {

    private String label;
    private T value;

    private String keyword;

    private boolean parameter;
    private boolean constant;

    private SketchDefineDirective directive;
    private LinkedList<SketchFunctionCall> computedFunctions;

    public SketchVariable(T value, String keyword, boolean parameter, boolean constant) {

        this.value = value;
        this.keyword = keyword;

        this.constant = constant;
        this.parameter = parameter;

        this.computedFunctions = new LinkedList<>();

    }

    public SketchVariable(String label, T value, String keyword, boolean parameter, boolean constant) {

        this.label = label;
        this.value = value;

        this.keyword = keyword;

        this.constant = constant;
        this.parameter = parameter;

        this.computedFunctions = new LinkedList<>();

        //
        // Restricciones de nombres de Variables
        //
        // Funciona para validar que no se añada
        // una variable que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        if(label.matches("^\\d+.*|.*\\s+.*"))
            throw new ArduinoRestrictionException("La variable '" + label + "' inicia con un numero o contine espacios.");

        if(label.matches("^[!@#$%^&*].*"))
            throw new ArduinoRestrictionException("La variable '" + label + "' contiene caracteres especiales que no son validos.");

        for(String kword : ArduinoRestrictions.KEYWORDS)
            if(label.equals(kword))
                throw new ArduinoRestrictionException("La variable '" + label + "' no puede ser llamada '" + kword + "' debido a que es una palabra clave reservada por el lenguaje.");

        // Fin de las restricciones

    }

    public void setDirectiveAsValue(SketchDefineDirective<?> directive) {
        this.directive = directive;
    }

    public void addComputedFunction(SketchFunctionCall call) {

        computedFunctions.add(call);
        call.saveResult(this);
        return;

    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        if(isParameter()) {

            buffer.append((isConstant() ? "const " : "") + getKeyword() + " " + getLabel(), false);
            return true;

        }

        if(getLabel() == null) {
            buffer.appendLine(getValue().toString());
            return true;
        }

        if(isDirectiveAsValue()) {

            buffer.appendLine((isConstant() ? "const " : "") + getKeyword() + " " + getLabel() + " = " + getDirective().getLabel() + ";");
            return true;

        }

        if(getValue() == null)
            buffer.appendLine((isConstant() ? "const " : "") + getKeyword() + " " + getLabel() + ";");
        else
            buffer.appendLine((isConstant() ? "const " : "") + getKeyword() + " " + getLabel() + " = " + getValue() + ";");

        return true;

    }

    public abstract SketchInstruction redefineVariable(T value);

    public abstract SketchInstruction operateVariable(T value, E operator, Object...args);

    public abstract String getParametizedForm();

    public abstract String getLiteralValue();

    public String getLabel() {
        return label;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean isConstant() {
        return constant;
    }

    public boolean isParameter() {
        return parameter;
    }

    public boolean isDirectiveAsValue() {
        return directive != null;
    }

    public SketchDefineDirective getDirective() {
        return directive;
    }

    public String getAsReference() {
        return "&" + getLabel();
    }

    public String getAsPointer() {
        return "*" + getLabel();
    }

    public Object getValue() {

        if(computedFunctions.size() > 0) {

            StringBuffer buffer = new StringBuffer();

            if(computedFunctions.size() == 1) {

                SketchFunctionCall call = computedFunctions.get(0);
                return value == null ? call.getNativeLiteralCall(false) : call.getNativeLiteralCall(false) + " + " +  value;

            }

            for(int i = 0; i < computedFunctions.size(); i++) {

                SketchFunctionCall call = computedFunctions.get(i);
                buffer.append(computedFunctions.get(i).getNativeLiteralCall(false));

                if(i >= computedFunctions.size())
                    break;

                buffer.append(" ");

            }

            if(value != null)
                buffer.append(" " + value);

            return buffer.toString();

        }

        return value;

    }

}
