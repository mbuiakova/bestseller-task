package com.example.logic.model.dto;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

/**
 * Encapsulates the user menu: contains the available drinks and toppings.
 */
@Getter
@AllArgsConstructor
public class Menu implements Serializable {
    private final Collection<BasicDrink> drinks;
    private final Collection<Topping> toppings;
}
