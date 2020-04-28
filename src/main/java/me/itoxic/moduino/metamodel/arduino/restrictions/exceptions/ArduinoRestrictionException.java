package me.itoxic.moduino.metamodel.arduino.restrictions.exceptions;

/**
 *
 * This is the general validation exception
 * when the arduino code wont compile.
 *
 */
public class ArduinoRestrictionException extends RuntimeException {

    @Deprecated
    public ArduinoRestrictionException(RestrictionExceptionType type) {
        super(type.getDeclaration());
    }

    public ArduinoRestrictionException(String customType) {
        super(customType);
    }

}
