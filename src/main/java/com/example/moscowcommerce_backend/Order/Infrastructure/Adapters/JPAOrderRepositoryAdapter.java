package com.example.moscowcommerce_backend.Order.Infrastructure.Adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Core.Domain.OrderId;
import com.example.moscowcommerce_backend.Order.Core.Domain.Ports.IOrderRepository;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;
import com.example.moscowcommerce_backend.Order.Infrastructure.Mappers.OrderMapper;
import com.example.moscowcommerce_backend.Order.Infrastructure.Ports.JpaOrderRepository;

@Repository
public class JPAOrderRepositoryAdapter implements IOrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Autowired
    public JPAOrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = mapToEntity(order);
        OrderEntity savedEntity = jpaOrderRepository.save(orderEntity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaOrderRepository.findById(id.value())
                .map(this::mapToDomain);
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream()
                .map(this::mapToDomain).toList();
    }

    private OrderEntity mapToEntity(Order order) {
        return OrderMapper.mapToEntity(order);
    }

    private Order mapToDomain(OrderEntity entity) {
        return OrderMapper.mapToDomain(entity);
    }

}
