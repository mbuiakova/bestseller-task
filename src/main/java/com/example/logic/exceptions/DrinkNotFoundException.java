package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such drink")
public class DrinkNotFoundException extends RuntimeException {

    public DrinkNotFoundException(final String drinkName, final Collection<String> toppings) {
        super(String.format("Drink %s with toppings %s is not found", drinkName, toppings));
    }
}
