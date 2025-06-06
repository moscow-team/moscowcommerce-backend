package com.example.moscowcommerce_backend.Order.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Order.Core.Domain.PaidPayment;
import com.example.moscowcommerce_backend.Order.Core.Domain.Payment;
import com.example.moscowcommerce_backend.Order.Core.Domain.PaymentId;
import com.example.moscowcommerce_backend.Order.Core.Domain.PaymentState;
import com.example.moscowcommerce_backend.Order.Core.Domain.PendingPayment;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.PaymentEntity;
import java.math.BigDecimal;

public abstract class PaymentMapper {
  public static PaymentEntity mapToEntity(Payment payment) {
    PaymentEntity paymentEntity = new PaymentEntity();
    if (payment.getId() != null) {
      paymentEntity.setId(payment.getId().value());
    }
    paymentEntity.setAmount(payment.getAmount().doubleValue());
    paymentEntity.setPaymentMethod(payment.getPaymentMethod());
    paymentEntity.setPaymentStatus(payment.getState().getName());
    return paymentEntity;
  }

  public static Payment mapToDomain(PaymentEntity entity) {
    Payment payment = new Payment();
    payment.setId(new PaymentId(entity.getId()));
    payment.setAmount(new BigDecimal(entity.getAmount()));
    payment.setPaymentMethod(entity.getPaymentMethod());
    payment.changeState(getState(entity.getPaymentStatus()));
    return payment;
  }

  private static PaymentState getState(PaymentStatus status) {
    if (status == PaymentStatus.PENDING) {
      return new PendingPayment(status);
    } else {
      return new PaidPayment(status);
    }
  }
}
