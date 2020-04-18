package me.itoxic.moduino.metamodel.arduino.entries.sketch.operations;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.generator.type.arduino.validator.InstructionDependent;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchOperation;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

public class SketchElseIfOperation extends SketchOperation implements InstructionDependent {

    private SketchIfOperation mainOperation;
    private SketchCondition condition;

    public SketchElseIfOperation(SketchIfOperation mainOperation, SketchCondition condition) {
        super(null);

        //
        // Restricciones de operador primario
        //
        // Funciona para validar que no se añada
        // un ELSE IF sin un IF previamente
        // añadido.
        //

        if(mainOperation == null)
            throw new ArduinoRestrictionException("No se puede generar una operación ELSE IF sin un operador principal IF definido correctamente.");

        // Fin de la restricción

        this.mainOperation = mainOperation;
        this.condition = condition;

        mainOperation.addElseIfOperation(this);

    }

    public SketchIfOperation getMainOperation() {
        return mainOperation;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.append(" else if(", false);

        condition.appendCodeLiteral(buffer);
        appendBasicOperationBody(buffer, true);

        return true;
    }

}
