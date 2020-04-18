package me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.type;

public enum SketchConditionJoinerType {

    AND("&&"),
    OR("||")

    ;

    private String literalJoiner;

    SketchConditionJoinerType(String literalJoiner) {
        this.literalJoiner = literalJoiner;
    }

    public String getLiteralJoiner() {
        return literalJoiner;
    }

}
