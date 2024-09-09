package com.refer.packages.exceptions;

public class DuplicateReferralException extends RuntimeException {
    public DuplicateReferralException() {
        super();
    }

    public DuplicateReferralException(String message) {
        super(message);
    } 
}
