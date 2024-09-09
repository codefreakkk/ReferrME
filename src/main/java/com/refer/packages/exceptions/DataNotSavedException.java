package com.refer.packages.exceptions;

public class DataNotSavedException extends RuntimeException {
    public DataNotSavedException() {
        super();
    }

    public DataNotSavedException(String message) {
        super(message);
    }
}
