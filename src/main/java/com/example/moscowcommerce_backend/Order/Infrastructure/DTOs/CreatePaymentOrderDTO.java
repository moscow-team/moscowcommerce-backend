package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentOrderDTO {
  @NotNull(message = "El tipo de pago es obligatorio")
  private PaymentType paymentMethod;
}
