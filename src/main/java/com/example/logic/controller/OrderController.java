package com.example.logic.controller;

import com.example.logic.service.OrderService;
import com.example.logic.model.Order;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.Cart;
import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.request.AddDrinkRequest;
import com.example.logic.request.PlaceOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService service;

    /**
     * Adds a drink to the cart.
     * @param request Contains all the necessary data to make the request.
     * @return A cart object to which the drink was added.
     */
    @PostMapping("/addDrinkToCart")
    public Cart addDrinkToCart(@NonNull @RequestBody final AddDrinkRequest request) {
        request.validate();

        if (request.getCartId() != null) {
            service.validateCartId(request.getCartId());
        }

        final DrinkWithToppings drink = service.getDrinkWithToppings(request.getDrink(), request.getToppings());

        return service.addDrinkToTheCart(request.getCartId(), drink);
    }

    /**
     * Places an order, deletes a temporary cart.
     * @param request Contains all the necessary data to make the request.
     * @return An Order object representing the order placed.
     */
    @PostMapping("/placeOrder")
    public Order placeOrder(@NonNull @RequestBody final PlaceOrderRequest request) {
        request.validate();

        return service.placeOrder(request.getCartId());
    }

    /**
     * Returns a cart by id.
     * @param cartId The ID of the cart to return.
     * @return A cart object requested.
     */
    @GetMapping("/getCart")
    public Cart getCart(@NonNull @PathVariable final UUID cartId) {
        return service.getCartById(cartId);
    }

    /**
     * Returns a menu, consisting of all available drinks and toppings.
     * @return A menu object.
     */
    @GetMapping("/getMenu")
    public Menu getMenu() {
        return service.getMenu();
    }
}
