package com.example.moscowcommerce_backend.Order.Infrastructure.Entities;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentStatus;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "amount")
  private double amount;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  private PaymentType paymentMethod;

  @Column(name = "payment_status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  public PaymentEntity() {}

  public PaymentEntity(
      int id, double amount, PaymentType paymentMethod, PaymentStatus paymentStatus) {
    this.id = id;
    this.amount = amount;
    this.paymentMethod = paymentMethod;
    this.paymentStatus = paymentStatus;
  }
}
