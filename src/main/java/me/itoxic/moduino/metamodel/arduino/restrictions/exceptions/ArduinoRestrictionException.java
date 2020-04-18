package me.itoxic.moduino.metamodel.arduino.restrictions.exceptions;

public class ArduinoRestrictionException extends RuntimeException {

    public ArduinoRestrictionException(RestrictionExceptionType type) {
        super(type.getDeclaration());
    }

    public ArduinoRestrictionException(String customType) {
        super(customType);
    }

}
