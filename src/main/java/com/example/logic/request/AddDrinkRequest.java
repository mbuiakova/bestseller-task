package com.example.logic.request;

import com.example.logic.exceptions.BadDrinkAddRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDrinkRequest implements Serializable {

    private String drink;
    private Map<String, Integer> toppings;
    private UUID cartId;

    public void validate() {
        if (getDrink() == null) {
            throw new BadDrinkAddRequest();
        }
    }
}
