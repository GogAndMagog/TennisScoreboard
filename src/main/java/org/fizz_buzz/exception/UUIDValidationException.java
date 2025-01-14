package org.fizz_buzz.exception;

public class UUIDValidationException extends RuntimeException{
    private static final String NOT_VALID_UUID = "Parameter %s is not valid UUID";

    public UUIDValidationException(String parameterValue) {
        super(NOT_VALID_UUID.formatted(parameterValue));
    }
}
