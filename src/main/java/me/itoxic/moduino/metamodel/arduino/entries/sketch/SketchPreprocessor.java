package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.metamodel.arduino.restrictions.ArduinoRestrictions;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public abstract class SketchPreprocessor implements SketchInstruction {

    private String keyword;
    private String label;

    public SketchPreprocessor(String keyword, String label) {

        this.keyword = keyword;
        this.label = label;

        //
        // Restricciones de nombres de directivas
        //
        // Funciona para validar que no se añada
        // una directiva que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        if(label.matches("^\\d+.*|.*\\s+.*"))
            throw new ArduinoRestrictionException("La directiva '" + label + "' inicia con un numero o contine espacios.");

        if(label.matches("^[!@#$%^&*].*"))
            throw new ArduinoRestrictionException("La directiva '" + label + "' contiene caracteres especiales que no son validos.");

        for(String kword : ArduinoRestrictions.KEYWORDS)
            if(label.equals(kword))
                throw new ArduinoRestrictionException("La directiva '" + label + "' no puede ser llamada '" + kword + "' debido a que es una palabra clave reservada por el lenguaje.");

        // Fin de las restricciones

    }

    public String getLabel() {
        return label;
    }

    public String getKeyword() {
        return keyword;
    }

}
