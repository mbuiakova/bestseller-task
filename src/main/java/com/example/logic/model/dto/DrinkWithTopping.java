package com.example.logic.model.dto;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class DrinkWithTopping {

    private BasicDrink drink;
    private Topping topping;
    private BigDecimal price;

    public DrinkWithTopping(BasicDrink drink, Topping topping) {
        this.drink = drink;
        this.topping = topping;
        this.price = drink.getPrice().add(topping.getPrice());
    }
}
