package com.example.utils.numeric;

/**
 * Useful utility functions for working with float numbers.
 */
public class FloatUtils {

    // The float data type is a single-precision 32-bit IEEE 754 floating-point number, which has a precision of approximately 6-7 decimal digits.

    /**
     * Returns true if the passed value is very close to zero and can be considered equal to zero.
     * @param value The value to check.
     * @return true if the value is very close to zero, false otherwise.
     */
    public static boolean isZero(final float value) {
        return Math.abs(value) < 1e-6;
    }
}
