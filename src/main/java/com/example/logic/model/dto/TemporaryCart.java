package com.example.logic.model.dto;

import com.example.logic.model.discounts.Discount;
import com.example.logic.model.discounts.DrinksCountThresholdOneFreeDiscount;
import com.example.logic.model.discounts.PriceThresholdPercentageDiscount;
import com.example.utils.json.DiscountSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
public class TemporaryCart implements Serializable {
    private UUID cartId;
    private List<DrinkWithToppings> drinks;
    private BigDecimal totalPrice;
    @JsonSerialize(using = DiscountSerializer.class)
    private Discount appliedDiscount = null;

    private static final Set<Discount> POSSIBLE_DISCOUNTS = Set.of(
        new PriceThresholdPercentageDiscount(BigDecimal.valueOf(13), 25.0f),
        new DrinksCountThresholdOneFreeDiscount(3)
    );

    public TemporaryCart() {
        this(new ArrayList<>());
    }

    public TemporaryCart(final DrinkWithToppings drink) {
        this(new ArrayList<>());
        addDrink(drink);
    }

    public TemporaryCart(final List<DrinkWithToppings> drinks) {
        this.cartId = UUID.randomUUID();
        this.drinks = drinks;
        countSum();
    }

    private void countSum() {
        totalPrice = drinks.stream()
                .map(DrinkWithToppings::getPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));

        final Optional<Discount> applicableDiscount = POSSIBLE_DISCOUNTS.stream().filter(discount -> discount.canBeApplied(this))
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
