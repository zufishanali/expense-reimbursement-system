package com.revature.reimbursementApi.exceptions;

public class UserNotPermittedException extends RuntimeException{
    public UserNotPermittedException() {
        super();
    }

    public UserNotPermittedException(String message) {
        super(message);
    }

    public UserNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotPermittedException(Throwable cause) {
        super(cause);
    }

    protected UserNotPermittedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
