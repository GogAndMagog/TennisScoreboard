package org.fizz_buzz.exception;

public class SomethingNotFound extends RuntimeException {
    private final static String SOMETHING_NOT_FOUND = "%s: %s not found";

    public SomethingNotFound(String whatNotFound, String value) {
        super(SOMETHING_NOT_FOUND.formatted(whatNotFound, value));
    }
}
