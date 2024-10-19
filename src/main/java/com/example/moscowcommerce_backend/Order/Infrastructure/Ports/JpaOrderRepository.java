package com.example.moscowcommerce_backend.Order.Infrastructure.Ports;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Integer> {

    
}
