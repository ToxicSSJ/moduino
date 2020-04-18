package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.type.arduino.validator.InstructionDependent;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;
import me.itoxic.moduino.util.ClassUtil;

import java.util.LinkedList;
import java.util.UUID;

public abstract class SketchContext {

    private UUID contextID;
    private SketchContext father;

    private LinkedList<SketchInstruction> instructions;
    private LinkedList<SketchContext> childs;

    public SketchContext() {
        this(null);
    }

    public SketchContext(SketchContext father) {

        this.contextID = UUID.randomUUID();

        this.instructions = new LinkedList<>();
        this.childs = new LinkedList<>();

    }

    public void setFather(SketchContext father) {

        if(father == this)
            throw new ArduinoRestrictionException("No se puede añadir el mismo contexto en sí.");

        this.father = father;

    }

    public void addChild(SketchContext child) {

        if(child == this)
            throw new ArduinoRestrictionException("No se puede añadir el mismo contexto en sí.");

        childs.add(child);

    }

    public void addInstructions(SketchInstruction...instructions) {
        for(SketchInstruction instruction : instructions)
            addInstruction(instruction);
    }

    public void addInstruction(SketchInstruction instruction) {

        //
        // Restricciones de tipo de Instrucción
        //
        // Funciona para validar que no se añada
        // una instrucción que impida compilar el codigo.
        // Se basa en el lenguaje de programación C.
        //

        if(instruction == this)
            throw new ArduinoRestrictionException("No se puede añadir una instrucción que sea igual a su contexto.");

        if(instruction instanceof InstructionDependent)
            throw new ArduinoRestrictionException("No se pueden incluir instrucciones dependientes de bases previas a un contexto literal.");

        if(instruction instanceof SketchFunction)
            throw new ArduinoRestrictionException("No se pueden añadir funciones a un contexto que no sea el general (padre).");

        if(instruction instanceof SketchVariable) {

            SketchVariable variable = (SketchVariable) instruction;

            for(SketchVariable cache : ClassUtil.getElementsInside(SketchVariable.class, instructions))
                if(variable == cache)
                    throw new ArduinoRestrictionException("La variable '" + variable.getLabel() + "' ya existe en el contexto del contenedor.");
                else if(variable.getLabel().equals(cache.getLabel()))
                    throw new ArduinoRestrictionException("La variable '" + variable.getLabel() + "' ya existe en el contexto del contenedor.");

        }

        if(instruction.getType() == InstructionType.INSTRUCTION_VARIABLE_USED) {

            //isBefore(instructions.getLast(), )

        }

        if(instruction instanceof SketchContext) {

            SketchContext context = (SketchContext) instruction;

            addChild(context);
            context.setFather(this);

        }

        // Fin de las restricciones

        instructions.add(instruction);

    }

    public LinkedList<SketchInstruction> getInstructions() {
        return instructions;
    }

    public int getAnyInstructionPosition(SketchInstruction instruction) {

        int position = getInstructionPosition(instruction);

        if(position == -1)
            if(!isRoot())
                return getFather().getUpperInstructionPosition(instruction);
            else
                return position;

        return position;

    }

    public int getUpperInstructionPosition(SketchInstruction instruction) {

        int position = getInstructionPosition(instruction);

        if(position == -1)
            if(!isRoot())
                return getFather().getUpperInstructionPosition(instruction);
            else
                return position;

        return position;

    }

    public int getUpperInstructionPosition(SketchInstruction base, SketchInstruction instruction) {

        int position = getInstructionPosition(instruction);

        if(position == -1)
            if(!isRoot())
                return getFather().getUpperInstructionPosition(instruction);
            else
                return position;

        return position;

    }

    public int getInstructionPosition(SketchInstruction instruction) {
        return getInstructions().indexOf(instruction);
    }

    public boolean isBefore(SketchInstruction before) {

        SketchInstruction base;

        if(!instructions.isEmpty())
            base = instructions.getLast();

        //getUpperInstructionPosition(before)

        return false;

    }

    public UUID getContextID() {
        return contextID;
    }

    public SketchContext getFather() {
        return father;
    }

    public boolean isRoot() {
        return father != null;
    }

    public enum InstructionType {

        INSTRUCTION_VARIABLE_USED,
        INSTRUCTION_SKETCH_OTHER,

        ;

    }

}
