package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.UUID;

/**
 * An exception which should be thrown in case a drink cannot be found.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such drink")
public class DrinkNotFoundException extends RuntimeException {

    public DrinkNotFoundException(final String drinkName, final Collection<String> toppings) {
        super(String.format("Drink \"%s\" with toppings %s is not found.", drinkName, toppings));
    }

    public DrinkNotFoundException(final String drinkName) {
        super(String.format("Drink \"%s\" doesn't exist.", drinkName));
    }

    public DrinkNotFoundException(final UUID drinkId) {
        super(String.format("Drink with id=%s doesn't exist.", drinkId));
    }
}
