package com.refer.packages.exceptions;

public class RaiseReferralRequestException extends RuntimeException {
    public RaiseReferralRequestException() {
        super();
    }

    public RaiseReferralRequestException(String message) {
        super(message);
    }
}
