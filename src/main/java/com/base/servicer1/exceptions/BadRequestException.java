package com.base.servicer1.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final LocalDateTime timestamp;

    public BadRequestException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }
}
