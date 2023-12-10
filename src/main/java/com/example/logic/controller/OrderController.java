package com.example.logic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PutMapping("/orderDrink")
    public Drink orderDrink(Type type, Topping topping) {

    }

    @GetMapping("/getCart")
    public Cart getCart(UserId userId) {

    }
}
