package com.example.logic.model.discounts;

import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.model.dto.Cart;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class DrinksCountThresholdOneFreeDiscount extends Discount implements Serializable {

    private final int drinksCountThreshold;

    public DrinksCountThresholdOneFreeDiscount(final int drinksCountThreshold) {
        super(String.format("A discount for a cart with total drinks count higher than %d. The cheapest item in the cart is free.", drinksCountThreshold));
        if (drinksCountThreshold < 1) {
            throw new IllegalArgumentException("Number of drinks cannot be less than 1");
        }
        this.drinksCountThreshold = drinksCountThreshold;
    }

    @Override
    public boolean canBeApplied(final Cart cart) {
        return cart.getDrinks().size() >= drinksCountThreshold;
    }

    @Override
    public BigDecimal getDiscountAmount(final Cart cart) {
        return cart.getDrinks().stream().map(DrinkWithToppings::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
    }
}