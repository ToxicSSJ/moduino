package me.itoxic.moduino.metamodel.arduino.entries.sketch.function.generated;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.metamodel.arduino.entries.Board;
import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchInstruction;
import me.itoxic.moduino.metamodel.arduino.entries.type.DigitalPinValue;
import me.itoxic.moduino.metamodel.arduino.entries.type.PinMode;

import java.util.List;

public class SketchSetup extends SketchFunction {

    private Board board;

    public SketchSetup(Board board) {
        super("setup", true);

        this.board = board;

    }

    public Board getBoard() {
        return board;
    }

    @Override
    public boolean appendCodeLiteral(CodeBuffer buffer) {

        buffer.appendLine(getReturnType().getType() + " " + getLabel() + "() {");
        buffer.getSpaceInfo().addContext();

        List<Pin> usedPins = board.getUsedPins();

        appendPinsModes(buffer, usedPins);
        appendPinsCleaner(buffer, usedPins);

        for(SketchInstruction instruction : getInstructions())
            instruction.appendCodeLiteral(buffer);

        buffer.getSpaceInfo().removeContext();
        buffer.appendLine("}");

        buffer.appendBreakline();

        return true;

    }

    public void appendPinsModes(CodeBuffer buffer, List<Pin> pins) {

        if(pins.size() > 0)
            buffer.appendBreakline();

        for(Pin pin : pins)
            if(pin.isUsed() && pin.getPinMode() == PinMode.OUTPUT)
                buffer.appendLine("pinMode(" + pin.getLabel() + ", " + pin.getPinMode().name() + ");");

    }

    public void appendPinsCleaner(CodeBuffer buffer, List<Pin> pins) {

        if(pins.size() > 0)
            buffer.appendBreakline();

        for(Pin pin : pins)
            if(pin.getPinMode() == PinMode.OUTPUT && pin.isUsed())
                pin.appendWriteLiteral(buffer, DigitalPinValue.LOW);

        if(pins.size() > 0)
            buffer.appendBreakline();

    }

}
