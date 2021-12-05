package com.yp.twitterclonebackend.exception;

public class UserEmailAlreadyExists extends RuntimeException {
    public UserEmailAlreadyExists(String message) {
        super(message);
    }

    public UserEmailAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
