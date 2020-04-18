package me.itoxic.moduino.metamodel.arduino.restrictions.exceptions;

public enum RestrictionExceptionType {

    ;

    private String declaration;

    RestrictionExceptionType(String declaration) {
        this.declaration = declaration;
    }

    public String getDeclaration() {
        return declaration;
    }

}
