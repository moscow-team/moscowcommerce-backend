package com.example.moscowcommerce_backend.Order.Core.Application.Ports;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import java.util.List;

public interface IListOrdersUseCase {
  public List<Order> listOrders();
}
