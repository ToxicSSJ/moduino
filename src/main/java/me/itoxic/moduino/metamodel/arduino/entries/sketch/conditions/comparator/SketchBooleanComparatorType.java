package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.comparator;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchBooleanVariable;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public enum SketchBooleanComparatorType {

    EQUALS("%s == %s"),
    NOT_EQUALS("%s != %s")

    ;

    private String literalComparation;

    SketchBooleanComparatorType(String literalComparation) {
        this.literalComparation = literalComparation;
    }

    public String processComparation(SketchBooleanVariable first, SketchBooleanVariable second) {

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

        if(second.getLabel() == null)
            if((Boolean) second.getValue() == true)
                return first.getParametizedForm();
            else
                return "!" + first.getParametizedForm();

        return String.format(literalComparation, first.getParametizedForm(), second.getParametizedForm());

    }

    public String getLiteralComparation() {
        return literalComparation;
    }

}
