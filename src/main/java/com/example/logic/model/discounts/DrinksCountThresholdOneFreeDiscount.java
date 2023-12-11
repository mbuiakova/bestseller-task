package com.example.logic.model.discounts;

import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.model.dto.TemporaryCart;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class DrinksCountThresholdOneFreeDiscount extends Discount implements Serializable {
    private final Integer drinksCountThreshold;

    public DrinksCountThresholdOneFreeDiscount(final Integer drinksCountThreshold) {
        super(String.format("A discount for a cart with total drinks count higher than %d. The cheapest item in the cart is free.", drinksCountThreshold));
        this.drinksCountThreshold = drinksCountThreshold;
    }

    @Override
    public boolean canBeApplied(final TemporaryCart cart) {
        return cart.getDrinks().size() >= drinksCountThreshold;
    }

    @Override
    public BigDecimal getDiscountAmount(final TemporaryCart cart) {
        return cart.getDrinks().stream().map(DrinkWithToppings::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
    }
}