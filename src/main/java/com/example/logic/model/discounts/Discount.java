package com.example.logic.model.discounts;

import com.example.logic.model.dto.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * An abstract class for all the discounts.
 */
@AllArgsConstructor
@Getter
public abstract class Discount implements Serializable {

    private final String description;

    /**
     * This method determines if this discount can be applied to the
     * cart specified.
     *
     * @param cart The cart on which to test whether the discount can be applied.
     * @return true if the discount can be applied, false otherwise.
     */

    public abstract boolean canBeApplied(final Cart cart);

    /**
     * Calculates the amount that will be removed from the total cart price
     * if this discount is applied.
     *
     * @param cart The cart to calculate the discount amount for.
     * @return The discount amount that can be applied to this cart.
     */
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