package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such cart")
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(final UUID cartId) {
        super(String.format("Cart with id=%s does not exist.", cartId));
    }
}
