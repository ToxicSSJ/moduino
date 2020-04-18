package me.itoxic.moduino.metamodel.arduino.entries.type;

public enum DigitalPinValue {

    HIGH(1),
    LOW(0)

    ;

    private int value;

    DigitalPinValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
