package me.itoxic.moduino.metamodel.arduino.entries.sketch.preloads;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.*;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.LinkedList;

public enum SketchNativeFunctionType {

    DELAY(null, "delay", SketchIntegerVariable.class),
    MILLIS(SketchLongVariable.class, "millis"),

    SERIALPRINT(null, "Serial.print", SketchStringVariable.class),
    SERIALPRINTLN(null, "Serial.printLn", SketchStringVariable.class),

    SERIALBEGIN(null, "Serial.begin", SketchIntegerVariable.class),
    SERIALAVAILABLE(SketchBooleanVariable.class, "Serial.available"),

    TONE(null, "tone", String.class, SketchFloatVariable.class, SketchFloatVariable.class),
    RANDOMSEED(null, "randomSeed", SketchIntegerVariable.class),
    RANDOM(SketchIntegerVariable.class, "random", SketchIntegerVariable.class, SketchIntegerVariable.class),

    ANALOGREAD(SketchFloatVariable.class, "analogRead", String.class),
    DIGITALREAD(SketchFloatVariable.class, "digitalRead", String.class),

    ANALOGWRITE(null, "analogWrite", String.class, SketchFloatVariable.class),
    DIGITALWRITE(null, "digitalWrite", String.class, SketchIntegerVariable.class)

    ;

    private Class<? extends SketchVariable> result;

    private String literal;
    private Class<?>[] parameters;

    SketchNativeFunctionType(Class<? extends SketchVariable> result, String literal, Class<?>...parameters) {

        this.result = result;
        this.literal = literal;

        this.parameters = parameters;

    }

    public String createCallAndSave(String resultLabel, LinkedList<String> literalDirectives, LinkedList<SketchVariable> params, boolean semicolon) {

        //
        // Restricciones de cantidad de parametros
        //
        // Funciona para validar que la cantidad de parametros
        // dada sea equivalente a las necesarias para la
        // llamada dinamica de la función.
        //

        if(params.size() + literalDirectives.size() != parameters.length)
            throw new ArduinoRestrictionException("La cantidad de parametros usados para llamar la función nativa '" + name() + "' no coinciden.");

        // Fin de las restricciones

        int parampos = 0;
        StringBuilder call = new StringBuilder();

        call.append(resultLabel + " = " + literal + "(");

        for(String directive : literalDirectives) {

            Class<?> clazz = parameters[parampos];

            if(clazz.isInstance(directive))
                call.append(directive + (parameters.length - 1 == parampos ? "" : ", "));
            else
                throw new ArduinoRestrictionException("Se está utilizando un tipo de variable invalida para la función nativa '" + this.name() + "', se debe utilizar una directiva tipo: '" + clazz.getName() + "'.");

            parampos++;

        }

        for(SketchVariable sketchVariable : params) {

            Class<?> clazz = parameters[parampos];

            if(clazz.isInstance(sketchVariable))
                call.append(sketchVariable.getParametizedForm() + (parameters.length - 1 == parampos ? "" : ", "));
            else
                throw new ArduinoRestrictionException("Se está utilizando un tipo de variable invalida para la función nativa '" + this.name() + "', se debe utilizar '" + clazz.getName() + "' en vez de '" + sketchVariable.getClass().getName() + "'.");

            parampos++;

        }

        call.append(")" + (semicolon ? ";" : ""));
        return call.toString();

    }

    public String createCall(LinkedList<String> literalDirectives, LinkedList<SketchVariable> params, boolean semicolon) {

        //
        // Restricciones de cantidad de parametros
        //
        // Funciona para validar que la cantidad de parametros
        // dada sea equivalente a las necesarias para la
        // llamada dinamica de la función.
        //

        if(params.size() + literalDirectives.size() != parameters.length)
            throw new ArduinoRestrictionException("La cantidad de parametros usados para llamar la función nativa '" + name() + "' no coinciden.");

        // Fin de las restricciones

        int parampos = 0;
        StringBuilder call = new StringBuilder();

        call.append(literal + "(");

        for(String directive : literalDirectives) {

            Class<?> clazz = parameters[parampos];

            if(clazz.isInstance(directive))
                call.append(directive + (parameters.length - 1 == parampos ? "" : ", "));
            else
                throw new ArduinoRestrictionException("Se está utilizando un tipo de variable invalida para la función nativa '" + this.name() + "', se debe utilizar una directiva tipo: '" + clazz.getName() + "'.");

            parampos++;

        }

        for(SketchVariable sketchVariable : params) {

            Class<?> clazz = parameters[parampos];

            if(clazz.isInstance(sketchVariable))
                call.append(sketchVariable.getParametizedForm() + (parameters.length - 1 == parampos ? "" : ", "));
            else if(clazz == SketchFloatVariable.class && sketchVariable.getClass() == SketchDoubleVariable.class)
                call.append(sketchVariable.getParametizedForm() + (parameters.length - 1 == parampos ? "" : ", "));
            else
                throw new ArduinoRestrictionException("Se está utilizando un tipo de variable invalida para la función nativa '" + this.name() + "', se debe utilizar '" + clazz.getName() + "' en vez de '" + sketchVariable.getClass().getName() + "'.");

            parampos++;

        }

        call.append(")" + (semicolon ? ";" : ""));
        return call.toString();

    }

    public String createCall(LinkedList<SketchVariable> params, boolean semicolon) {

        //
        // Restricciones de cantidad de parametros
        //
        // Funciona para validar que la cantidad de parametros
        // dada sea equivalente a las necesarias para la
        // llamada dinamica de la función.
        //

        if(params.size() != parameters.length)
            throw new ArduinoRestrictionException("La cantidad de parametros usados para llamar la función nativa '" + name() + "' no coinciden.");

        // Fin de las restricciones

        int parampos = 0;
        StringBuilder call = new StringBuilder();

        call.append(literal + "(");

        for(int i = 0; i < parameters.length; i++) {

            Class<?> clazz = parameters[i];
            SketchVariable variable = params.get(i);

            if(clazz.isInstance(variable))
                call.append(variable.getParametizedForm() + (parameters.length - 1 == i ? "" : ", "));

        }

        call.append(")" + (semicolon ? ";" : ""));
        return call.toString();

    }

    public String getLabel() {
        return literal;
    }

    public Class<? extends SketchVariable> getResult() {
        return result;
    }

}
