package com.example.moscowcommerce_backend.Order.Infrastructure.Ports;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Integer> {}
