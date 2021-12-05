package com.yp.twitterclonebackend.exception;

public class AccessNotAllowedException extends RuntimeException {
    public AccessNotAllowedException(String message) {
        super(message);
    }

    public AccessNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
