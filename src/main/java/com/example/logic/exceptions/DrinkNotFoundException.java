package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;

/**
 * An exception which should be thrown in case a drink cannot be found.
 */
public class DrinkNotFoundException extends ResponseStatusException {

    public DrinkNotFoundException(final String drinkName, final Collection<String> toppings) {
        super(HttpStatus.NOT_FOUND, String.format("Drink \"%s\" with toppings %s is not found.", drinkName, toppings));
    }

    public DrinkNotFoundException(final String drinkName) {
        super(HttpStatus.NOT_FOUND, String.format("Drink \"%s\" doesn't exist.", drinkName));
    }

    public DrinkNotFoundException(final UUID drinkId) {
        super(HttpStatus.NOT_FOUND, String.format("Drink with id=%s doesn't exist.", drinkId));
    }
}
