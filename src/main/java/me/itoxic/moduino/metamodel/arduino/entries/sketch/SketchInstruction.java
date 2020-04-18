package me.itoxic.moduino.metamodel.arduino.entries.sketch;

import me.itoxic.moduino.generator.buffer.CodeBuffer;

public interface SketchInstruction {

    boolean appendCodeLiteral(CodeBuffer buffer);

    default SketchContext.InstructionType getType() {
        return SketchContext.InstructionType.INSTRUCTION_SKETCH_OTHER;
    }

}
