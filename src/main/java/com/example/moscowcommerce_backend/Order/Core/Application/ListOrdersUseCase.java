package com.example.moscowcommerce_backend.Order.Core.Application;

import com.example.moscowcommerce_backend.Order.Core.Application.Ports.IListOrdersUseCase;
import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Core.Domain.Ports.IOrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListOrdersUseCase implements IListOrdersUseCase {
  private final IOrderRepository orderRepository;

  @Autowired
  public ListOrdersUseCase(IOrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public List<Order> listOrders() {
    return orderRepository.findAll();
  }
}
