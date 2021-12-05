package com.yp.twitterclonebackend.exception;

public class UserNotActivate extends RuntimeException {
    public UserNotActivate(String message) {
        super(message);
    }

    public UserNotActivate(String message, Throwable cause) {
        super(message, cause);
    }
}
