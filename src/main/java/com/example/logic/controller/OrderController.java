package com.example.logic.controller;

import com.example.logic.service.OrderService;
import com.example.logic.exceptions.DrinkNotFoundException;
import com.example.logic.model.Order;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.Cart;
import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.request.AddDrinkRequest;
import com.example.logic.request.PutOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService service;

    @PostMapping("/addDrinkToCart")
    public Cart addDrinkToCart(@NonNull @RequestBody final AddDrinkRequest request) {
        request.validate();

        final DrinkWithToppings drink = service.getDrinkWithToppings(request.getDrink(), request.getToppings());

        if (drink == null) {
            throw new DrinkNotFoundException(request.getDrink(), request.getToppings().keySet());
        }

        return service.addDrinkToTheCart(request.getCartId(), drink);
    }

    @PostMapping("/putOrder")
    public Order putOrder(@NonNull @RequestBody final PutOrderRequest request) {
        request.validate();
        return service.putOrder(request.getCartId());
    }

    @GetMapping("/getCart")
    public Cart getCart(@NonNull @PathVariable final UUID cartId) {
        return service.getCartById(cartId);
    }

    @GetMapping("/getMenu")
    public Menu getMenu() {
        return service.getMenu();
    }
}
