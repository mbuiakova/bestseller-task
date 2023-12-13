package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * A global exception handler that beatifully wraps the exceptions before sending them back as a response.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(final ResponseStatusException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getReason());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}