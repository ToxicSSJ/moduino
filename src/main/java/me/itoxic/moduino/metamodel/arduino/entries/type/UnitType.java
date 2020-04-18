package me.itoxic.moduino.metamodel.arduino.entries.type;

public enum UnitType {

    MILLISECONDS(1),
    SECONDS(1000),
    MINUTES(60000);

    private int multiplier;

    UnitType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

}
