package com.example.moscowcommerce_backend.Order.Infrastructure.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Order.Core.Application.CreateOrderUseCase;
import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.CreateOrderDTO;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.ResultOrderDTO;
import com.example.moscowcommerce_backend.Order.Infrastructure.Mappers.OrderMapper;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    @Autowired
    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<Result<ResultOrderDTO>> createOrder(@RequestBody @Valid CreateOrderDTO order) {
        try {
            String userEmail = getAuthenticatedUserEmail();
            Order orderSaved = this.createOrderUseCase.create(order, userEmail);
            return ResponseEntity.ok(Result.success(OrderMapper.mapToResult(orderSaved), "Orden creada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.failure(e.getMessage()));
        }
    }

    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // Normalmente el username es el email
        } else {
            return principal.toString();
        }
    }
}
