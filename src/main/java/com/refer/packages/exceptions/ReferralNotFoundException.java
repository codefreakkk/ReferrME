package com.refer.packages.exceptions;

public class ReferralNotFoundException extends RuntimeException {
    public ReferralNotFoundException() {
        super();
    }

    public ReferralNotFoundException(String message) {
        super(message);
    }
}
