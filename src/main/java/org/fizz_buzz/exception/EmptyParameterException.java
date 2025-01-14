package org.fizz_buzz.exception;

public class EmptyParameterException extends RuntimeException {
    private final static String PARAMETER_MUST_NOT_BE_EMPTY = "Parameter %s must not be empty";

    public EmptyParameterException(String parameterName) {
        super(PARAMETER_MUST_NOT_BE_EMPTY.formatted(parameterName));
    }
}
