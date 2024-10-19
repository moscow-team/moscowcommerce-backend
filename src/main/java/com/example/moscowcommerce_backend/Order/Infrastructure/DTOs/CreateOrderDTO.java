package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    @NotBlank(message = "Payment es obligatorio")
    private CreatePaymentOrderDTO payment;
   
    @NotBlank(message = "Shipment es obligatorio")
    private CreateShipmentOrderDTO shipment;

    @NotBlank(message = "Products es obligatorio")
    @Size(min = 1, message = "Debe haber al menos un producto")
    private List<ProductOrderDTO> products;
}
