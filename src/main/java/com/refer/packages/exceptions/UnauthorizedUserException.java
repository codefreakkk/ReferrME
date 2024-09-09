package com.refer.packages.exceptions;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException() {
        super();
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
