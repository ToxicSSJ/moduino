package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.generator.type.arduino.validator.InstructionDependent;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.type.SketchConditionJoinerType;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.HashMap;
import java.util.Map;

public abstract class SketchCondition implements SketchInstruction, InstructionDependent {

    private Map<SketchCondition, SketchConditionJoinerType> joined;

    public SketchCondition() {

        this.joined = new HashMap<>();

    }

    public void joinCondition(SketchCondition condition, SketchConditionJoinerType joinerType) {

        //
        // Restricciones de unión de condiciones
        //
        // Funciona para validar que no se unan
        // dos condiciones iguales. (es innecesario)
        //

        if(condition == this)
            throw new ArduinoRestrictionException("No puedes unir dos condiciones iguales.");

        if(joined.containsKey(condition))
            throw new ArduinoRestrictionException("La condición ya fue comprobada en la general.");

        // Fin de las restricciones

        joined.put(condition, joinerType);

    }

    public int getConditionsQuantity() {
        return 1 + joined.size();
    }

    public abstract String getLiteralCondition();

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.append(getLiteralCondition(), false);

        for(Map.Entry<SketchCondition, SketchConditionJoinerType> entry : joined.entrySet()) {

            buffer.append(" " + entry.getValue().getLiteralJoiner() + " ", false);

            if(entry.getKey().getConditionsQuantity() >= 2) {

                buffer.append("(", false);
                entry.getKey().appendCodeLiteral(buffer);
                buffer.append(")", false);
                continue;

            }

            entry.getKey().appendCodeLiteral(buffer);

        }

        return true;

    }

}
