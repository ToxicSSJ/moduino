package me.itoxic.moduino.metamodel.arduino.entries.sketch.variables;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchIncludeDirective;
import me.itoxic.moduino.metamodel.arduino.restrictions.ArduinoRestrictions;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public abstract class SketchLibraryVariable<T extends SketchIncludeDirective> implements SketchInstruction {

    private String label;

    private String keyword;

    private boolean parameter;
    private boolean constant;

    public SketchLibraryVariable(String customKeyword, T includeDirective, boolean parameter, boolean constant) {

        this.keyword = customKeyword;

        this.constant = constant;
        this.parameter = parameter;

    }

    public SketchLibraryVariable(String customKeyword, String label, T includeDirective, boolean parameter, boolean constant) {

        this.label = label;

        this.keyword = customKeyword;

        this.constant = constant;
        this.parameter = parameter;

        //
        // Restricciones de nombres de Variables
        // de libreria
        //
        // Funciona para validar que no se añada
        // una variable o clase que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        if(label.matches("^\\d+.*|.*\\s+.*"))
            throw new ArduinoRestrictionException("La variable '" + label + "' inicia con un numero o contine espacios.");

        if(label.matches("^[!@#$%^&*].*"))
            throw new ArduinoRestrictionException("La variable '" + label + "' contiene caracteres especiales que no son validos.");

        for(String kword : ArduinoRestrictions.KEYWORDS)
            if(label.equals(kword) || keyword.equals(kword))
                throw new ArduinoRestrictionException("La variable o su clase '" + label + "' no puede ser llamada '" + kword + "' debido a que es una palabra clave reservada por el lenguaje.");

        // Fin de las restricciones

    }

    @Override
    public abstract boolean appendCodeLiteral(CodeBuffer buffer);

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

}
