package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;
import com.example.moscowcommerce_backend.Shared.Domain.StatePattern.State;

public abstract class PaymentState extends State<Payment> {
  private PaymentStatus name;

  public PaymentState(PaymentStatus name) {
    this.validateStatus(name);
  }

  public PaymentStatus getName() {
    return name;
  }

  private void validateStatus(PaymentStatus name) {
    if (name != PaymentStatus.PENDING && name != PaymentStatus.PAID) {
      throw new IllegalArgumentException("Invalid status");
    }

    this.name = name;
  }
}
