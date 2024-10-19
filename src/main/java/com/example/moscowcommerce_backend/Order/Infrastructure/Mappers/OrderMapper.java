package com.example.moscowcommerce_backend.Order.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

public abstract class OrderMapper {
    public static OrderEntity mapToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId().value());
        orderEntity.setUser(UserMapper.toEntity(order.getUser()));
        orderEntity.setProducts(order.getProducts().stream()
                .map(product -> ProductMapper.toEntity(product))
                .toList());
        orderEntity.setPayment(PaymentMapper.mapToEntity(order.getPayment()));
        orderEntity.setShipment(ShipmentMapper.mapToEntity(order.getShipment()));
        orderEntity.setCreationDate(order.getCreateTime());
        orderEntity.setModificationDate(order.getUpdateTime());
        orderEntity.setArchivedDate(order.getArchivedTime());
        return orderEntity;
    }

    public static Order mapToDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                UserMapper.toDomainFromEntity(entity.getUser()),
                entity.getProducts().stream().map(product -> ProductMapper.toDomainFromEntity(product)).toList(),
                PaymentMapper.mapToDomain(entity.getPayment()),
                ShipmentMapper.mapToDomain(entity.getShipment()),
                entity.getCreationDate(),
                entity.getModificationDate(),
                entity.getArchivedDate()
        );
    }
}
