package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception which should be thrown in case of a malformed add drink request.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid add drink request")
public class BadDrinkAddRequest extends RuntimeException {

    public BadDrinkAddRequest() {
        super("Drink %s with toppings %s is not found");
    }
}
