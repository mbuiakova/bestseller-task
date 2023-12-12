package com.example.logic.model.discounts;

import com.example.logic.model.dto.Cart;
import com.example.utils.numeric.FloatUtils;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A discount which can be applied to a cart, based on the total price
 * in the given cart.
 */
@Getter
public class PriceThresholdPercentageDiscount extends Discount implements Serializable {
    private final BigDecimal priceThreshold;
    private final float discountPercentage;

    public PriceThresholdPercentageDiscount(final BigDecimal priceThreshold, final float discountPercentage) {
        super(String.format("A discount for a cart with total price higher than %s with percentage of %s%%.", priceThreshold, discountPercentage));
        if (discountPercentage <= 0 || FloatUtils.isZero(discountPercentage) ) {
            throw new IllegalArgumentException("Discount cannot be less than 0");
        }
        if (priceThreshold.intValue() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.priceThreshold = priceThreshold;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean canBeApplied(final Cart cart) {
        return cart.getTotalPrice().compareTo(priceThreshold) > 0;
    }

    @Override
    public BigDecimal getDiscountAmount(final Cart cart) {
        if (Float.isNaN(discountPercentage) || Float.isInfinite(discountPercentage) || FloatUtils.isZero(discountPercentage)) {
            return BigDecimal.ZERO;
        }

        return cart.getTotalPrice().multiply(BigDecimal.ONE.divide(BigDecimal.valueOf(discountPercentage), 2, RoundingMode.UNNECESSARY));
    }
}