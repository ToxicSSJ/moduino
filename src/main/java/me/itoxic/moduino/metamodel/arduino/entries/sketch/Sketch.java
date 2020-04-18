package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.Board;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.generated.SketchLoop;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.function.generated.SketchSetup;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.util.LinkedList;

public class Sketch implements SketchInstruction {

    private SketchSetup setupFunction;
    private SketchLoop loopFunction;

    private LinkedList<SketchPreprocessor> preprocessors;
    private LinkedList<SketchInstruction> instructions;
    private LinkedList<SketchVariable> variables;
    private LinkedList<SketchFunction> functions;

    public Sketch(Board board) {

        this.setupFunction = new SketchSetup(board);
        this.loopFunction = new SketchLoop();

        this.preprocessors = new LinkedList<>();
        this.variables = new LinkedList<>();
        this.instructions = new LinkedList<>();
        this.functions = new LinkedList<>();

    }

    public void addPreprocessorDirective(SketchPreprocessor directive) {

        //
        // Sketch Restricción de Preprocesadores
        //
        // Funciona para validar que no se añada
        // la misma directiva o el mismo label
        // de preprocesador.
        //

        // Fin de la Restricción

        preprocessors.add(directive);

    }

    public void addInstruction(SketchInstruction instruction) {
        instructions.add(instruction);
    }

    public void addVariables(SketchVariable...variables) {
        for(SketchVariable variable : variables)
            addVariable(variable);
    }

    public void addVariable(SketchVariable variable) {

        //
        // Sketch Restricción de Variables
        //
        // Funciona para validar que no se añada
        // la misma variable o el mismo label
        // de variable.
        //

        for(SketchVariable v : variables)
            if(v == variable)
                throw new ArduinoRestrictionException("La variable " + v.getLabel() + " ya fue añadida al contexto general del Sketch.");
            else if(v.getLabel().equals(variable.getLabel()))
                throw new ArduinoRestrictionException("Ya existe una variable del mismo nombre '" + v.getLabel() + "' que fue añadida al contexto general del Sketch.");

        // Fin de la Restricción

        variables.add(variable);

    }

    public void addFunctions(SketchFunction...functions) {
        for(SketchFunction function : functions)
            addFunction(function);
    }

    public void addFunction(SketchFunction function) {

        //
        // Sketch Restricción de Funciones
        //
        // Funciona para validar que no se añada
        // la misma función o el mismo label
        // de una función.
        //

        for(SketchFunction f : functions)
            if(f == function)
                throw new ArduinoRestrictionException("La función " + f.getLabel() + " ya fue añadida al contexto general del Sketch.");
            else if(f.getLabel().equals(function.getLabel()))
                throw new ArduinoRestrictionException("Ya existe una función del mismo nombre '" + f.getLabel() + "' que fue añadida al contexto general del Sketch.");

        if(function.getLabel().equals("setup") || function.getLabel().equals("loop"))
            throw new ArduinoRestrictionException("La función que se intenta añadir ya está reservada para el funcionamiento del sistema.");

        // Fin de la Restricción

        functions.add(function);

    }

    public LinkedList<SketchVariable> getVariables() {
        return variables;
    }

    public LinkedList<SketchFunction> getFunctions() {
        return functions;
    }

    public SketchSetup getSetupFunction() {
        return setupFunction;
    }

    public SketchLoop getLoopFunction() {
        return loopFunction;
    }

    public int getVariablePosition(SketchVariable variable) {
        return getVariables().indexOf(variable);
    }

    public int getFunctionPosition(SketchFunction function) {
        return getFunctions().indexOf(function);
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        for(SketchPreprocessor preprocessor : preprocessors)
            preprocessor.appendCodeLiteral(buffer);

        if(preprocessors.size() > 0)
            buffer.appendBreakline();

        for(SketchVariable variable : variables)
            variable.appendCodeLiteral(buffer);

        if(variables.size() > 0)
            buffer.appendBreakline();

        for(SketchInstruction instruction : instructions)
            instruction.appendCodeLiteral(buffer);

        if(instructions.size() > 0)
            buffer.appendBreakline();

        setupFunction.appendCodeLiteral(buffer);
        loopFunction.appendCodeLiteral(buffer);

        for(SketchFunction function : functions)
            function.appendCodeLiteral(buffer);

        return true;

    }

}
