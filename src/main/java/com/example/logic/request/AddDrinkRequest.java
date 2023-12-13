package com.example.logic.request;

import com.example.logic.exceptions.BadDrinkAddRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * An object which is used as a REST request for adding a drink to a cart.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDrinkRequest implements Serializable {

    private String drink;
    private Map<String, Integer> toppings;

    /**
     * Validates the objects fields.
     * @throws BadDrinkAddRequest If the object is malformed.
     */
    public void validate() {
        if (getDrink() == null) {
            throw new BadDrinkAddRequest();
        }
    }
}
