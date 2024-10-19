package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;

public class PendingPayment extends PaymentState {

    public PendingPayment(PaymentStatus statusName) {
        super(statusName);
    }
}
