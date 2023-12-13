package com.example.logic.controller;

import com.example.logic.service.OrderService;
import com.example.logic.model.Order;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.Cart;
import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.request.AddDrinkRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService service;

    /**
     * Creates a new empty cart.
     * @return A cart object to which the drink was added.
     */
    @PostMapping("/cart")
    public Cart createNewCart() {
        return service.createNewCart();
    }

    /**
     * Adds a drink to the cart.
     * @param cartId The ID of the cart to which the drink must be added.
     * @param request Contains all the necessary data to make the request.
     * @return A cart object to which the drink was added.
     */
    @PostMapping("/cart/{cartId}/drinks")
    public Cart addDrinkToCart(
            @NonNull @PathVariable final UUID cartId,
            @NonNull @RequestBody final AddDrinkRequest request
    ) {
        request.validate();
        service.validateCartId(cartId);

        final DrinkWithToppings drink = service.getDrinkWithToppings(request.getDrink(), request.getToppings());

        return service.addDrinkToTheCart(cartId, drink);
    }

    /**
     * Places an order, deletes a temporary cart.
     * @param cartId Contains all the necessary data to make the request.
     * @return An Order object representing the order placed.
     */
    @PostMapping("/order/{cartId}")
    public Order placeOrder(@NonNull @PathVariable final UUID cartId) {
        return service.placeOrder(cartId);
    }

    /**
     * Returns a cart by id.
     * @param cartId The ID of the cart to return.
     * @return A cart object requested.
     */
    @GetMapping("/cart/{cartId}")
    public Cart getCart(@NonNull @PathVariable final UUID cartId) {
        return service.getCartById(cartId);
    }

    /**
     * Returns a menu, consisting of all available drinks and toppings.
     * @return A menu object.
     */
    @GetMapping("/menu")
    public Menu getMenu() {
        return service.getMenu();
    }
}
