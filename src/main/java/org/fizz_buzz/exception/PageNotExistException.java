package org.fizz_buzz.exception;

public class PageNotExistException extends RuntimeException {
    private static final String PAGE_NOT_EXIST = "Page %d does not exists";

    public PageNotExistException(int pageNumber) {
        super(PAGE_NOT_EXIST.formatted(pageNumber));
    }
}
