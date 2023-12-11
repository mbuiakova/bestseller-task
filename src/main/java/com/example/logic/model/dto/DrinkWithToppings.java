package com.example.logic.model.dto;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import com.example.utils.json.ToppingMapSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class DrinkWithToppings implements Serializable {
    private final UUID combinationId = UUID.randomUUID();
    private final BasicDrink drink;

    /**
     * A map of topping to quantity.
     */
    @JsonSerialize(using = ToppingMapSerializer.class)
    private final Map<Topping, Integer> toppings = new HashMap<>();

    public DrinkWithToppings(final BasicDrink drink, final Map<Topping, Integer> toppings) {
        this.drink = drink;

        if (toppings != null) {
            this.toppings.putAll(toppings);
        }
    }

    public DrinkWithToppings(final BasicDrink drink, final Collection<Topping> toppings) {
        this.drink = drink;

        if (toppings != null) {
            toppings.forEach(this::addTopping);
        }
    }

    /**
     * Calculates the price of the drink with all the toppings.
     * @return The total price for the drink with the toppings.
     */
    public BigDecimal getPrice() {
        return toppings.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0))
                .add(drink.getPrice());
    }

    public void addTopping(final Topping topping) {
        toppings.put(topping, toppings.getOrDefault(topping, 0) + 1);
    }

    public void removeTopping(final Topping topping) {
        if (toppings.containsKey(topping)) {
            final Integer quantity = toppings.get(topping);
            if (quantity <= 1) {
                toppings.remove(topping);
            } else {
                toppings.put(topping, quantity - 1);
            }
        }
    }
}
