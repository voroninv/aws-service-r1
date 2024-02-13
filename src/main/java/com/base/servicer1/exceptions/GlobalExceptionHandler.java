package com.base.servicer1.exceptions;

import com.amazonaws.AmazonServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(body(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<Object> handleAmazonException(AmazonServiceException ex) {
        return new ResponseEntity<>(body(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(body(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonException.class)
    public ResponseEntity<Object> handleJsonException(JsonException ex) {
        return new ResponseEntity<>(body(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static Map<String, Object> body(Throwable throwable) {
        Map<String, Object> body = new HashMap<>();
        body.put("exception", throwable.getClass().getName());
        body.put("message", throwable.getMessage());
        body.put("timestamp", LocalDateTime.now());
        logger.error(throwable.getClass().getName() + ": " + throwable.getMessage());

        return body;
    }
}
