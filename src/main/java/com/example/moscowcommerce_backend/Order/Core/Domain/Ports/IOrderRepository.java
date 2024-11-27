package com.example.moscowcommerce_backend.Order.Core.Domain.Ports;

import java.util.List;
import java.util.Optional;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Core.Domain.OrderId;

public interface IOrderRepository {
    public Order save(Order order);
    public Optional<Order> findById(OrderId id);
    public List<Order> findAll();    
} 
