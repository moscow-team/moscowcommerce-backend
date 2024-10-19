package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Shared.Domain.PriceValueObject;

import java.math.BigDecimal;

public class AmountPayment extends PriceValueObject{
    public AmountPayment(BigDecimal value) {
        super(value);
    } 
}
