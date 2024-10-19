package com.example.moscowcommerce_backend.Order.Infrastructure.DTOs;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.Enums.PaymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePaymentOrderDTO {
    @NotBlank(message = "El tipo de pago es obligatorio")
    @Pattern(regexp = "^(CREDIT|DEBIT|CASH|PAYPAL|MERCADO_PAGO)$", message = "El rol debe ser CREDIT o DEBIT o CASH o PAYPAL o MERCADO_PAGO) (mayúsculas)")
    private PaymentType paymentMethod;
}
