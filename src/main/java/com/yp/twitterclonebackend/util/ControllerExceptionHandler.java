package com.yp.twitterclonebackend.util;

import com.yp.twitterclonebackend.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
            TweetNotExistException.class
    })
    public ResponseEntity<ExceptionResponse> tweetNotExistException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse("존재하지 않는 트윗입니다.", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessNotAllowedException.class)
    public ResponseEntity<ExceptionResponse> accessNotAllowedException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse("권한이 없는 접근입니다.", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionResponse> userNotExistException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse("존재하지 않는 사용자입니다.", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExist(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse("이미 존재하는 사용자입니다.", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
