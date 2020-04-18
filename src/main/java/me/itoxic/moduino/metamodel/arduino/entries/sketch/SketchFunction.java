package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.type.SketchFunctionType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchLibraryVariable;
import me.itoxic.moduino.metamodel.arduino.restrictions.ArduinoRestrictions;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.LinkedList;

public class SketchFunction extends SketchContext implements SketchInstruction {

    private String label;

    private boolean nativeFunction;
    private SketchFunctionType returnType;

    private SketchInstruction resultInstruction;
    private SketchVariable resultVariable;
    private SketchLibraryVariable resultLibraryVariable;
    private SketchCondition resultCondition;

    private LinkedList<SketchVariable> parameters;

    public SketchFunction(String label) {
        this(label, SketchFunctionType.VOID, false);
    }

    public SketchFunction(String label, boolean nativeFunction) {
        this(label, SketchFunctionType.VOID, nativeFunction);
    }

    public SketchFunction(String label, SketchFunctionType returnType, boolean nativeFunction) {

        //
        // Restricciones de nombres de Funciones
        //
        // Funciona para validar que no se añada
        // una función que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        if(label.matches("^\\d+.*|.*\\s+.*"))
            throw new ArduinoRestrictionException("La función inicia con un numero o contine espacios.");

        if(label.matches("^[!@#$%^&*].*"))
            throw new ArduinoRestrictionException("La función contiene caracteres especiales que no son validos.");

        for(String kword : ArduinoRestrictions.KEYWORDS)
            if(label.equals(kword))
                throw new ArduinoRestrictionException("No puedes llamar a la función '" + kword + "' debido a que es una palabra clave reservada por el lenguaje.");

        // Fin de las restricciones

        this.label = label;
        this.nativeFunction = nativeFunction;

        this.returnType = returnType;
        this.parameters = new LinkedList<>();

    }

    public void addParameter(SketchVariable variable) {

        if(isNative())
            throw new ArduinoRestrictionException("No puedes añadir nuevos parametros a funciones nativas de Arduino.");

        //
        // Restricciones de tipo de Instrucción
        //
        // Funciona para validar que no se añada
        // una instrucción que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        for(SketchVariable cache : parameters)
            if(variable == cache)
                throw new ArduinoRestrictionException("El parametro '" + variable.getLabel() + "' ya es un parametro de la función '" + label + "'.");
            else if(variable.getLabel().equals(cache.getLabel()))
                throw new ArduinoRestrictionException("El parametro '" + variable.getLabel() + "' ya es un parametro de la función '" + label + "'.");

        if(!variable.isParameter())
            throw new ArduinoRestrictionException("La variable '" + variable.getLabel() + "' no fue definida como un parametro.");

        // Fin de las restricciones

        parameters.add(variable);

    }

    public void setResultInstruction(SketchInstruction instruction) {
        this.resultInstruction = instruction;
    }

    public void setResultOperation(SketchCondition sketchCondition) {

        if(returnType != SketchFunctionType.BOOLEAN)
            return;

        this.resultCondition = sketchCondition;

    }

    public void setResultVariable(SketchVariable resultVariable) {

        if(returnType == SketchFunctionType.VOID)
            return;

        if(resultVariable.getClass() == returnType.getResult())
            this.resultVariable = resultVariable;

    }

    public void setResultLibraryVariable(SketchLibraryVariable resultVariable) {

        if(returnType == SketchFunctionType.VOID)
            return;

        this.resultLibraryVariable = resultVariable;

    }

    public void setReturnType(SketchFunctionType sketchFunctionType) {
        this.returnType = sketchFunctionType;
    }

    public String getLabel() {
        return label;
    }

    public SketchFunctionType getReturnType() {
        return returnType;
    }

    public boolean isNative() {
        return nativeFunction;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.append(returnType.getType() + " " + label + "(", true);

        for(SketchVariable parameter : parameters)
            parameter.appendCodeLiteral(buffer);

        buffer.appendLine(") {");

        buffer.getSpaceInfo().addContext();

        for(SketchInstruction instruction : getInstructions())
            instruction.appendCodeLiteral(buffer);

        D : if(returnType != SketchFunctionType.VOID) {

            if(resultVariable == null && resultCondition == null && resultInstruction == null && resultLibraryVariable == null)
                break D;//throw new ArduinoRestrictionException("La función '" + getLabel() + "' no retorna ningún valor.");

            if(resultInstruction != null) {
                buffer.append("return ", true);
                resultInstruction.appendCodeLiteral(buffer);
                buffer.appendBreakline();
            } else if(resultVariable != null)
                buffer.appendLine("return " + resultVariable.getLabel() + ";");
            else if (resultLibraryVariable != null)
                buffer.appendLine("return " + resultLibraryVariable.getLabel() + ";");
            else
                buffer.appendLine("return " + resultCondition.getLiteralCondition() + ";");

        }

        buffer.getSpaceInfo().removeContext();
        buffer.appendLine("}");

        buffer.appendBreakline();
        return true;

    }

}
