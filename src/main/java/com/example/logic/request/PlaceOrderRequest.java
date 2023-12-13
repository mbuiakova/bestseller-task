package com.example.logic.request;

import com.example.logic.exceptions.CartNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * An object which is used as a REST request for finalising a cart and placing an order.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest implements Serializable {

    private UUID cartId;

    /**
     * Validates the objects fields.
     * @throws CartNotFoundException If the cart cannot be found.
     */
    public void validate() {
        if (cartId == null) {
            throw new CartNotFoundException(null);
        }
    }
}
