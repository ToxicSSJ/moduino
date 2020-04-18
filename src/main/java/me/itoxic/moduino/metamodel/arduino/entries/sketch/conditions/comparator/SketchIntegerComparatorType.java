package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.SketchFunctionCall;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchFloatVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchIntegerVariable;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public enum SketchIntegerComparatorType {

    EQUALS("%s == %s"),
    NOT_EQUALS("%s != %s"),

    GREATER_OR_EQUAL("%s >= %s"),
    LESS_OR_EQUAL("%s <= %s"),

    GREATER("%s > %s"),
    LESS("%s < %s")

    ;

    private String literalComparation;

    SketchIntegerComparatorType(String literalComparation) {
        this.literalComparation = literalComparation;
    }

    public String processComparation(SketchFunctionCall first, SketchFunctionCall second) {
        return String.format(literalComparation, first.getFunctionLiteralCall(false), second.getFunctionLiteralCall(false));
    }

    public String processComparation(SketchIntegerVariable first, SketchDefineDirective<Integer> second) {

        //
        // Restricciones de variables a comparar
        //
        // Funciona para validar que no se esten
        // comparando variables que sean solo
        // parametros.
        //

        if(first.isParameter())
            throw new ArduinoRestrictionException("Las variables a comparar son parametros.");

        // Fin de las restricciones

        return String.format(literalComparation, first.getParametizedForm(), second.getLabel());

    }

    public String processComparation(SketchIntegerVariable first, SketchIntegerVariable second) {

        //
        // Restricciones de variables a comparar
        //
        // Funciona para validar que no se esten
        // comparando variables que sean solo
        // parametros.
        //

        if(first.isParameter() || second.isParameter())
            throw new ArduinoRestrictionException("Las variables a comparar son parametros.");

        // Fin de las restricciones

        return String.format(literalComparation, first.getParametizedForm(), second.getParametizedForm());

    }

    public String processComparation(SketchInstruction instruction, SketchFloatVariable sketchFloatVariable) {

        CodeBuffer temp = new CodeBuffer("dummy", "dummy", false);
        instruction.appendCodeLiteral(temp);

        String literal = temp.getBufferCode().toString();
        return String.format(literalComparation, literal, sketchFloatVariable.getParametizedForm());

    }

    public String getLiteralComparation() {
        return literalComparation;
    }

}
