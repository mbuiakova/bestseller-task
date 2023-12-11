package com.example.logic.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class Cart {

    private UUID userId;
    private List<BasicDrink> drinks;
    private BigDecimal sum;

    public Cart(UUID userId, List<BasicDrink> drinks) {
        this.userId = userId;
        this.drinks = drinks;
        this.sum = countSum(drinks);
    }

    private BigDecimal countSum(List<BasicDrink> drinks) {
        Optional<BigDecimal> total = drinks.stream()
                .map(BasicDrink::getPrice)
                .reduce(BigDecimal::add);
        return total.orElse(new BigDecimal(-1));
    }
}
