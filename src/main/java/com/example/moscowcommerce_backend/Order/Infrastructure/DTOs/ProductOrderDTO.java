package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductOrderDTO {
    @NotBlank(message = "El productId es obligatorio")
    private Integer productId;

    @NotBlank(message = "La cantidad es obligatoria")
    private Integer quantity;
}
