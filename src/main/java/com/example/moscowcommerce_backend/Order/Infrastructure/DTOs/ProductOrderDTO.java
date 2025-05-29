package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductOrderDTO {
  @NotNull(message = "El productId es obligatorio")
  private Integer productId;

  @NotNull(message = "La cantidad es obligatoria")
  private Integer quantity;
}
