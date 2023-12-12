package com.example.logic.model.discounts;

import com.example.logic.model.dto.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public abstract class Discount implements Serializable {

    private final String description;

    public abstract boolean canBeApplied(final Cart cart);

    public abstract BigDecimal getDiscountAmount(final Cart cart);

    /**
     * Calculates the cart total price with the discount.
     *
     * @param cart The cart to calculate the discount for.
     * @return The total price of the cart with the discount applied.
     */
    public BigDecimal getCartTotalPriceWithDiscount(final Cart cart) {
        return cart.getTotalPrice().subtract(getDiscountAmount(cart));
    }
}