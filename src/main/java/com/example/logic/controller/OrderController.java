package com.example.logic.controller;

import com.example.logic.OrderService;
import com.example.logic.exceptions.BadDrinkAddRequest;
import com.example.logic.exceptions.DrinkNotFoundException;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.TemporaryCart;
import com.example.logic.model.dto.DrinkWithToppings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class AddDrink implements Serializable {
    private String drink;
    private Map<String, Integer> toppings;
    private UUID cartId;

    void validate() {
        if (getDrink() == null) {
            throw new BadDrinkAddRequest();
        }
    }
}

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService service;

    @PostMapping("/addDrinkToCart")
    public TemporaryCart addDrinkToCart(@NonNull @RequestBody AddDrink data) {
        data.validate();

        final DrinkWithToppings drink = service.getDrinkWithToppings(data.getDrink(), data.getToppings());

        if (drink == null) {
            throw new DrinkNotFoundException(data.getDrink(), data.getToppings().keySet());
        }

        return service.addDrinkToTheCart(data.getCartId(), drink);
    }

    @PostMapping("")
    public TemporaryCart putOrder(@NonNull @RequestParam final UUID cartId) {
        return service.putOrder(cartId);
    }

    @GetMapping("/getCart")
    public TemporaryCart getCart(@NonNull @PathVariable final UUID cartId) {
        return service.getTemporaryCartById(cartId);
    }

    @GetMapping("/getMenu")
    public Menu getMenu() {
        return service.getMenu();
    }
}
