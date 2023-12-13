package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * An exception which should be thrown in case a cart cannot be found.
 */
public class CartNotFoundException extends ResponseStatusException {

    public CartNotFoundException(final UUID cartId) {
        super(HttpStatus.NOT_FOUND, String.format("Cart with id=%s does not exist.", cartId));
    }
}
