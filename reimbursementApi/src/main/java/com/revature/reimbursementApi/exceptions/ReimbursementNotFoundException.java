package com.revature.reimbursementApi.exceptions;

public class ReimbursementNotFoundException extends RuntimeException {
    public ReimbursementNotFoundException() {
        super();
    }

    public ReimbursementNotFoundException(String message) {
        super(message);
    }

    public ReimbursementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReimbursementNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ReimbursementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
