package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;

public class ToppingNotFoundException extends ResponseStatusException {
    public ToppingNotFoundException(final UUID topping) {
        super(HttpStatus.NOT_FOUND, String.format("Topping with id=%s doesn't exist.", topping));
    }

    public ToppingNotFoundException(final Collection<String> toppingNames) {
        super(HttpStatus.NOT_FOUND, String.format("Toppings with these names do not exist: %s", toppingNames));
    }
}
