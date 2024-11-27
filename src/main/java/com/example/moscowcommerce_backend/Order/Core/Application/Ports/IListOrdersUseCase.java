package com.example.moscowcommerce_backend.Order.Core.Application.Ports;

import java.util.List;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;

public interface IListOrdersUseCase {
    public List<Order> listOrders();    
}
