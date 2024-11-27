package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    @Valid
    @NotNull(message = "El payment no puede ser nulo")
    private CreatePaymentOrderDTO payment;
   
    @Valid
    @NotNull(message = "El shipment no puede ser nulo")
    private CreateShipmentOrderDTO shipment;

    @Size(min = 1, message = "Debe haber al menos un producto")
    @NotNull(message = "El products no puede ser nulo")
    @Valid
    private List<ProductOrderDTO> products;
}
