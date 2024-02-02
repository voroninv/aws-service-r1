package com.base.servicer1.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LambdaNotFoundException extends RuntimeException {
    private final LocalDateTime timestamp;

    public LambdaNotFoundException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }
}
