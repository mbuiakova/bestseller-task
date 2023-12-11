package com.example.utils.numeric;

public class FloatUtils {

    // The float data type is a single-precision 32-bit IEEE 754 floating-point number, which has a precision of approximately 6-7 decimal digits.
    public static boolean isZero(float val) {
        return Math.abs(val) < 1e-6;
    }
}
