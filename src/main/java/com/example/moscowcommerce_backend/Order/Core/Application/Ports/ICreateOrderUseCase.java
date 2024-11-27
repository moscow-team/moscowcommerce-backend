package com.example.moscowcommerce_backend.Order.Core.Application.Ports;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.CreateOrderDTO;

public interface ICreateOrderUseCase {
    Order create(CreateOrderDTO order, String userEmail);
}
