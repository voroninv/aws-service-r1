package com.base.servicer1.exceptions;

import com.base.servicer1.constants.Constants;
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
        logger.error(ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("error", Constants.INTERNAL_SERVER_ERROR);
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LambdaNotFoundException.class)
    public ResponseEntity<Object> handleLambdaNotFoundException(LambdaNotFoundException ex) {
        logger.error(ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("error", Constants.NOT_FOUND);
        body.put("message", ex.getMessage());
        body.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        logger.error(ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("error", Constants.BAD_REQUEST);
        body.put("message", ex.getMessage());
        body.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonException.class)
    public ResponseEntity<Object> handleJsonException(JsonException ex) {
        logger.error(ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("error", Constants.INTERNAL_SERVER_ERROR);
        body.put("message", ex.getMessage());
        body.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
