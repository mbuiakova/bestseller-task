package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such topping")
public class ToppingNotFoundException extends RuntimeException {
    public ToppingNotFoundException(final UUID topping) {
        super(String.format("Topping with id=%s doesn't exist.", topping));
    }
}
