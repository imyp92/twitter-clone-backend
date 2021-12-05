package com.yp.twitterclonebackend.exception;

public class TweetNotExistException extends RuntimeException {
    public TweetNotExistException(String message) {
        super(message);
    }

    public TweetNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
