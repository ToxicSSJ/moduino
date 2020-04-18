package me.itoxic.moduino.metamodel.arduino.entries.sketch.operations;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchCondition;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchOperation;

import java.util.LinkedList;

public class SketchIfOperation extends SketchOperation {

    private SketchCondition condition;

    private LinkedList<SketchElseIfOperation> elseIfOperations;
    private SketchElseOperation elseOperation;

    public SketchIfOperation(SketchCondition condition) {
        super(null);

        this.condition = condition;
        this.elseIfOperations = new LinkedList<>();

    }

    public SketchIfOperation(String label) {
        super(label);
    }

    public void addElseIfOperation(SketchElseIfOperation operation) {
        elseIfOperations.add(operation);
    }

    public void setElseOperation(SketchElseOperation operation) {
        elseOperation = operation;
    }

    public SketchCondition getCondition() {
        return condition;
    }

    public LinkedList<SketchElseIfOperation> getElseIfOperations() {
        return elseIfOperations;
    }

    public SketchElseOperation getElseOperation() {
        return elseOperation;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.append((getLabel() != null ? getLabel() + " : " : "") + "if(", true);

        condition.appendCodeLiteral(buffer);
        appendBasicOperationBody(buffer, true);

        if(elseIfOperations.size() > 0)
            for(SketchElseIfOperation operation : elseIfOperations)
                operation.appendCodeLiteral(buffer);

        if(elseOperation != null)
            elseOperation.appendCodeLiteral(buffer);

        buffer.appendBreakline();

        return true;
    }

}
