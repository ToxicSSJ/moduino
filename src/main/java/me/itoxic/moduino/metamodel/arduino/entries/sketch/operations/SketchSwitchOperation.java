package me.itoxic.moduino.metamodel.arduino.entries.sketch.operations;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;

import java.util.LinkedList;

public class SketchSwitchOperation extends SketchOperation {

    private SketchVariable variable;

    private LinkedList<SketchCaseOperation> caseOperations;

    public SketchSwitchOperation(SketchVariable variable) {
        this(null, variable);
    }

    public SketchSwitchOperation(String label, SketchVariable variable) {
        super(label);

        this.variable = variable;
        this.caseOperations = new LinkedList<>();

    }

    public void addCaseOperation(SketchCaseOperation operation) {
        caseOperations.add(operation);
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.appendLine((getLabel() != null ? getLabel() + " : " : "") + "switch(" + variable.getLabel() + ") {");

        if(caseOperations.size() > 0) {

            buffer.getSpaceInfo().addContext();

            for(SketchCaseOperation ucase : caseOperations)
                ucase.appendCodeLiteral(buffer);

            buffer.getSpaceInfo().removeContext();

        }

        if(getInstructions().size() > 0) {

            buffer.getSpaceInfo().addContext();
            buffer.appendLine("default:");
            buffer.getSpaceInfo().addContext();

            for (SketchInstruction instruction : getInstructions())
                instruction.appendCodeLiteral(buffer);

            buffer.appendLine("break;");
            buffer.getSpaceInfo().removeContext();
            buffer.getSpaceInfo().removeContext();

        }

        buffer.appendLine("}");
        buffer.appendBreakline();

        return true;
    }

}
