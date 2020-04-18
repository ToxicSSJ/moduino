package me.itoxic.moduino.metamodel.arduino.entries.sketch.function.type;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchVariable;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.*;

public enum SketchFunctionType {

    VOID("void"),

    INTEGER("int", SketchIntegerVariable.class),
    DOUBLE("double", SketchDoubleVariable.class),
    FLOAT("float", SketchFloatVariable.class),
    LONG("long", SketchLongVariable.class),

    BOOLEAN("boolean", SketchBooleanVariable.class),
    STRING("String", SketchStringVariable.class)

    ;

    private String type;
    private Class<? extends SketchVariable> result;

    SketchFunctionType(String type) {
        this.type = type;
    }

    SketchFunctionType(String type, Class<? extends SketchVariable> result) {
        this.type = type;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public Class<? extends SketchVariable> getResult() {
        return result;
    }

}
