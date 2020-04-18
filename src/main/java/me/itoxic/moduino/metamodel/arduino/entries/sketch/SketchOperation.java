package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;

import java.util.LinkedList;

public abstract class SketchOperation extends SketchContext implements SketchInstruction {

    private String label;

    private LinkedList<SketchVariable> parameters;

    public SketchOperation(String label) {

        this.label = label;

        this.parameters = new LinkedList<>();

    }

    public boolean appendBasicOperationBody(CodeBuffer buffer, boolean usesParenthesis) {

        buffer.append((usesParenthesis ? ") " : "") + "{", false);

        if(getInstructions().size() > 0) {

            buffer.getSpaceInfo().addContext();
            buffer.appendBreakline();

            for(SketchInstruction instructions : getInstructions())
                instructions.appendCodeLiteral(buffer);

            buffer.getSpaceInfo().removeContext();

            buffer.append("}", true);
            return true;

        }

        buffer.appendBreakline();
        buffer.append("}", true);
        return true;

    }

    public String getLabel() {
        return label;
    }

    public LinkedList<SketchVariable> getParameters() {
        return parameters;
    }

}
