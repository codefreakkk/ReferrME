package com.refer.packages.exceptions;

public class SameCompanyException extends RuntimeException {
    public SameCompanyException() {
        super();
    }

    public SameCompanyException(String message) {
        super(message);
    }
}
