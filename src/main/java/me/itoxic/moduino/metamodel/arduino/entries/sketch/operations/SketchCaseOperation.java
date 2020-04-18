package me.itoxic.moduino.metamodel.arduino.entries.sketch.operations;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.generator.type.arduino.validator.InstructionDependent;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchOperation;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;

public class SketchCaseOperation extends SketchOperation implements InstructionDependent {

    private SketchVariable variable;
    private SketchDefineDirective directive;

    public SketchCaseOperation(SketchVariable variable) {
        super(null);

        this.variable = variable;

    }

    public SketchCaseOperation(SketchDefineDirective directive) {
        super(null);

        this.directive = directive;

    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        if(getInstructions().size() > 0) {

            buffer.appendLine("case " + (variable != null ? variable.getParametizedForm() : directive.getLabel()) + ":");
            buffer.getSpaceInfo().addContext();

            for (SketchInstruction instruction : getInstructions())
                instruction.appendCodeLiteral(buffer);

            buffer.appendLine("break;");
            buffer.getSpaceInfo().removeContext();

        }

        return true;
    }

}
