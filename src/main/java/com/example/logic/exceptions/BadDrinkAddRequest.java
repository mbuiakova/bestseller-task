package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid add drink request")
public class BadDrinkAddRequest extends RuntimeException {

    public BadDrinkAddRequest() {
        super("Drink %s with toppings %s is not found");
    }
}
