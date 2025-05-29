package com.example.moscowcommerce_backend.Order.Core.Domain.Ports;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Core.Domain.OrderId;
import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
  public Order save(Order order);

  public Optional<Order> findById(OrderId id);

  public List<Order> findAll();
}
