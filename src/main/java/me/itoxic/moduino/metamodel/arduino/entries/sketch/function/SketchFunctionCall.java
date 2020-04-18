package me.itoxic.moduino.metamodel.arduino.entries.sketch.function;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.type.SketchFunctionType;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preloads.SketchNativeFunctionType;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.LinkedList;

public class SketchFunctionCall implements SketchInstruction {

    private String functionLabel;
    private Class<? extends SketchVariable> result;

    private SketchVariable saveResult;
    private LinkedList<SketchVariable> callParameters;

    private SketchFunctionType sketchFunctionType;
    private SketchNativeFunctionType nativeFunction;

    public SketchFunctionCall(SketchFunction function, SketchVariable...parameters) {

        this.functionLabel = function.getLabel();
        this.callParameters = new LinkedList<>();
        this.sketchFunctionType = function.getReturnType();

        if(!function.getReturnType().equals(SketchFunctionType.VOID))
            result = function.getReturnType().getResult();

        for(SketchVariable parameter : parameters)
            callParameters.add(parameter);

    }

    public SketchFunctionCall(SketchNativeFunctionType nativeFunctionType, SketchVariable...parameters) {

        this.functionLabel = nativeFunctionType.getLabel();
        this.callParameters = new LinkedList<>();
        this.nativeFunction = nativeFunctionType;

        if(nativeFunctionType.getResult() != null)
            result = nativeFunctionType.getResult();

        for(SketchVariable parameter : parameters)
            callParameters.add(parameter);

    }

    public void addParameters(SketchVariable...parameters) {
        for(SketchVariable parameter : parameters)
            callParameters.add(parameter);
    }

    public void saveResult(SketchVariable variable) {

        //
        // Restricciones de resultado al llamado de una Función
        //
        // Funciona para validar que el resultado de una
        // función si sea compatible con la variable a la
        // cual se quiere adjudicar.
        //

        if(result == null)
            throw new ArduinoRestrictionException("La función '" + functionLabel + "' es de tipo VOID, por lo cual no se pueden referenciar variables al retorno de la función.");

        if(!result.isInstance(variable))
            throw new ArduinoRestrictionException("La variable a la cual se intenta adjudicar el resultado de la función '" + functionLabel + "' no es del tipo indicado, debe ser de tipo '" + result.getName() + "'.");

        // Fin de las restricciones

        this.saveResult = variable;

    }

    public String getFunctionLabel() {
        return functionLabel;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        if(nativeFunction != null) {

            buffer.append((saveResult != null ? saveResult.getLabel() + " = " : "") + nativeFunction.createCall(callParameters, true), true);
            buffer.appendBreakline();
            return true;

        }

        buffer.append((saveResult != null ? saveResult.getLabel() + " = " : "") + getFunctionLabel() + "(", true);

        if(callParameters.isEmpty()) {

            buffer.append(");", false);
            buffer.appendBreakline();
            return true;

        }

        for(SketchVariable parameter : callParameters)
            buffer.append(parameter.getParametizedForm(), false);

        buffer.appendLine(");");

        return true;

    }

    public SketchFunctionType getSketchFunctionType() {
        return sketchFunctionType;
    }

    public String getFunctionLiteralCall(boolean semicolon) {

        if(functionLabel != null)
            return functionLabel + "()" + (semicolon ? ";" : "");

        return "";

    }

    public String getNativeLiteralCall(boolean semicolon) {

        if(nativeFunction != null)
            return nativeFunction.createCall(callParameters, semicolon);

        return "";

    }

}
