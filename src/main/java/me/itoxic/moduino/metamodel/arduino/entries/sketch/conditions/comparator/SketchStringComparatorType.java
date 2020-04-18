package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchStringVariable;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public enum SketchStringComparatorType {

    EQUALS("%s.equals(%s)"),
    EQUALSIGNORECASE("%s.equalsIgnoreCase(%s)"),

    STARTSWITH("%s.startsWith(%s)"),
    ENDSWITH("%s.endsWith(%s)")

    ;

    private String literalComparation;

    SketchStringComparatorType(String literalComparation) {
        this.literalComparation = literalComparation;
    }

    public String processComparation(SketchStringVariable first, SketchStringVariable second) {

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

    public String getLiteralComparation() {
        return literalComparation;
    }

}
