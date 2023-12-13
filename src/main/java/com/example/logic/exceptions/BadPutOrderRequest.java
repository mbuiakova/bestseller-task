package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception which should be thrown in case a cart cannot be found.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid put order request")
public class BadPutOrderRequest extends RuntimeException {

    public BadPutOrderRequest() {
        super("The data for finalising the order is malformed.");
    }
}
