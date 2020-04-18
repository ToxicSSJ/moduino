package me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchPreprocessor;

public class SketchIncludeDirective extends SketchPreprocessor {

    private boolean header;

    public SketchIncludeDirective(String libraryName, boolean header) {
        super("include", libraryName);

        this.header = header;

    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.appendLine("#" + getKeyword() + " <" + getLabel() + (isHeader() ? ".h" : ".cpp") + ">");
        return true;

    }

    public boolean isHeader() {
        return header;
    }

}
