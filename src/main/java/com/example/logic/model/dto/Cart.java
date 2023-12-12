package com.example.logic.model.dto;

import com.example.logic.model.Order;
import com.example.logic.model.discounts.Discount;
import com.example.logic.model.discounts.DrinksCountThresholdOneFreeDiscount;
import com.example.logic.model.discounts.PriceThresholdPercentageDiscount;
import com.example.utils.json.DiscountSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Cart implements Serializable {

    private final UUID cartId = UUID.randomUUID();
    private final List<DrinkWithToppings> drinks = new ArrayList<>();
    private BigDecimal totalPrice;
    @JsonSerialize(using = DiscountSerializer.class)
    private Discount appliedDiscount = null;

    private static final Set<Discount> POSSIBLE_DISCOUNTS = Set.of(
        new PriceThresholdPercentageDiscount(BigDecimal.valueOf(12), 25.0f),
        new DrinksCountThresholdOneFreeDiscount(3)
    );

    public Cart(final DrinkWithToppings drink) {
        addDrink(drink);
    }

    public Cart(final List<DrinkWithToppings> drinks) {
        this.drinks.addAll(drinks);
        countSum();
    }

    private void countSum() {
        totalPrice = drinks.stream()
                .map(DrinkWithToppings::getPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));

        final Optional<Discount> applicableDiscount = POSSIBLE_DISCOUNTS.stream()
                .filter(discount -> discount.canBeApplied(this))
                .max(Comparator.comparing(d -> d.getDiscountAmount(this)));

        applicableDiscount.ifPresent(discount -> {
            totalPrice = discount.getCartTotalPriceWithDiscount(this);
            appliedDiscount = discount;
        });
    }

    public void addDrink(final DrinkWithToppings drink) {
        drinks.add(drink);
        countSum();
    }
}
