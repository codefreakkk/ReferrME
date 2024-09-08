package com.refer.packages.exceptions;

public class UserCVNotFoundException extends RuntimeException {
    public UserCVNotFoundException() {
        super();
    }

    public UserCVNotFoundException(String message) {
        super(message);
    }
}
