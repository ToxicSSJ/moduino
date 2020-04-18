package me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchPreprocessor;

public class SketchDefineDirective<T> extends SketchPreprocessor {

    private T value;

    public SketchDefineDirective(String label, T value) {
        super("define", label);

        this.value = value;

    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.appendLine("#" + getKeyword() + " " + getLabel() + " " + value);
        return true;

    }

    public T getValue() {
        return value;
    }

}
