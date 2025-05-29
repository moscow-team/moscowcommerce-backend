package com.example.moscowcommerce_backend.Order.Core.Domain;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentType;
import com.example.moscowcommerce_backend.Shared.Domain.PriceValueObject;
import com.example.moscowcommerce_backend.Shared.Domain.StatePattern.StateContext;
import java.math.BigDecimal;

public class Payment extends StateContext<PaymentState> {

  private PaymentId id;
  private PriceValueObject amount;
  private PaymentType paymentMethod;

  public Payment() {
    super(new PendingPayment(PaymentStatus.PENDING));
  }

  public Payment(Integer id, BigDecimal amount, PaymentType paymentMethod) {
    super(new PendingPayment(PaymentStatus.PENDING));
    this.id = new PaymentId(id);
    this.amount = new PriceValueObject(amount);
    this.paymentMethod = paymentMethod;
  }

  public Payment(PaymentType paymentMethod, PriceValueObject amount) {
    super(new PendingPayment(PaymentStatus.PENDING));
    this.amount = amount;
    this.paymentMethod = paymentMethod;
  }

  public PaymentId getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount.getValue();
  }

  public PaymentType getPaymentMethod() {
    return paymentMethod;
  }

  public void setId(PaymentId id) {
    this.id = id;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = new PriceValueObject(amount);
  }

  public void setPaymentMethod(PaymentType paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public void approve() {
    this.changeState(new PaidPayment(PaymentStatus.PAID));
  }
}
