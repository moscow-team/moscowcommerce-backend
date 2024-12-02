package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;

public class PaidPayment extends PaymentState {

    public PaidPayment(PaymentStatus statusName) {
        super(statusName);

    }
}
