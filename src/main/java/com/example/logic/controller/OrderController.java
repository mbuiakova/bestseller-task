package com.example.logic.controller;

import com.example.logic.OrderService;
import com.example.logic.exceptions.DrinkNotFoundException;
import com.example.logic.model.Cart;
import com.example.logic.model.BasicDrink;
import com.example.logic.model.dto.DrinkWithTopping;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {

    private OrderService service;

    private static Map<UUID, List<DrinkWithTopping>> crunchifyMap = ExpiringMap.builder()
            .expiration(5, TimeUnit.MINUTES)
            .build();

    @PostMapping("/addDrinkToChart")
    public UUID addDrinkToChart(String type, String topping, UUID sessionID) {
        DrinkWithTopping drink = service.getDrink(type, topping);
        if (drink == null) {
            throw new DrinkNotFoundException(type, topping);
        }
        if (sessionID == null || !crunchifyMap.containsKey(sessionID)) {
            sessionID = UUID.randomUUID();
        }
        crunchifyMap.compute(sessionID, (k, v) -> {
            v = v != null ? new ArrayList<>(v) : new ArrayList<>();
            v.add(drink);
            return v;
        });
        return sessionID;
    }

    @PostMapping("")
    public void putOrder(UUID sessionId) {
        List<DrinkWithTopping> drinkWithToppings = crunchifyMap.get(sessionId);

    }

    @GetMapping("/getCart")
    public Cart getCart(UUID userId) {
        return new Cart();
    }
}
