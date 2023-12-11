package com.example.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such drink")  // 404
public class DrinkNotFoundException extends RuntimeException {

    public DrinkNotFoundException(String type, String topping) {
        super("Drink " + "with topping " + topping + "is not found");
    }
}
