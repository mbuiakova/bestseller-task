package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * An exception which should be thrown in case of a malformed add drink request.
 */
public class BadDrinkAddRequest extends ResponseStatusException {

    public BadDrinkAddRequest() {
        super(HttpStatus.BAD_REQUEST, "Drink %s with toppings %s is not found");
    }
}
