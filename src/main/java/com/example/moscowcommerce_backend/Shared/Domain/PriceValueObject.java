package com.example.moscowcommerce_backend.Shared.Domain;

import java.math.RoundingMode;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object for representing a Price in the system.
 */
public class PriceValueObject {

    private final BigDecimal value;

    /**
     * Constructs a Price instance.
     *
     * @param value the price value
     * @throws IllegalArgumentException if the value is null, negative, or contains more than 2 decimal places.
     */
    public PriceValueObject(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Price cannot be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (value.scale() > 2) {
            throw new IllegalArgumentException("Price cannot have more than two decimal places.");
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    /**
     * Adds another Price to this one.
     *
     * @param other the other price to add
     * @return a new Price with the sum of the two values
     */
    public PriceValueObject add(PriceValueObject other) {
        return new PriceValueObject(this.value.add(other.value));
    }

    /**
     * Adds a double to this Price.
     * @param other
     * @return
     */
    public PriceValueObject add(double other) {
        return new PriceValueObject(this.value.add(BigDecimal.valueOf(other)));
    }

    /**
     * Subtracts another Price from this one.
     *
     * @param other the other price to subtract
     * @return a new Price with the result of the subtraction
     * @throws IllegalArgumentException if the result is negative.
     */
    public PriceValueObject subtract(PriceValueObject other) {
        return new PriceValueObject(this.value.subtract(other.value));
    }

    /**
     * Multiplies this price by a factor.
     *
     * @param factor the factor to multiply by
     * @return a new Price with the result of the multiplication
     */
    public PriceValueObject multiply(BigDecimal factor) {
        return new PriceValueObject(this.value.multiply(factor));
    }

    /**
     * Divides this price by a divisor.
     *
     * @param divisor the divisor to divide by
     * @return a new Price with the result of the division
     * @throws IllegalArgumentException if the divisor is zero.
     */
    public PriceValueObject divide(BigDecimal divisor) {
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return new PriceValueObject(this.value.divide(divisor, 2, RoundingMode.HALF_UP));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceValueObject price = (PriceValueObject) o;
        return value.compareTo(price.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
