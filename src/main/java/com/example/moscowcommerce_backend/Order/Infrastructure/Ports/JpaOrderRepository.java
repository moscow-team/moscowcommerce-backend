package com.example.moscowcommerce_backend.Order.Infrastructure.Ports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Integer>{}
