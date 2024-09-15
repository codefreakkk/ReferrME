package com.refer.packages.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException() {
        super();
    }

    public InvalidStatusException(String message) {
        super(message);
    }
}
