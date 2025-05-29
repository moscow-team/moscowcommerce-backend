package com.example.moscowcommerce_backend.Order.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Order.Core.Domain.Order;
import com.example.moscowcommerce_backend.Order.Infrastructure.DTOs.ResultOrderDTO;
import com.example.moscowcommerce_backend.Order.Infrastructure.Entities.OrderEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import java.util.List;

public abstract class OrderMapper {
  public static OrderEntity mapToEntity(Order order) {
    OrderEntity orderEntity = new OrderEntity();
    if (order.getId() != null) {
      orderEntity.setId(order.getId().value());
    }
    orderEntity.setUser(UserMapper.toEntity(order.getUser()));
    orderEntity.setProducts(
        order.getProducts().stream().map(product -> ProductMapper.toEntity(product)).toList());
    orderEntity.setPayment(PaymentMapper.mapToEntity(order.getPayment()));
    orderEntity.setShipment(ShipmentMapper.mapToEntity(order.getShipment()));

    if (order.getCreateTime() != null) {
      orderEntity.setCreationDate(order.getCreateTime());
    }
    if (order.getUpdateTime() != null) {
      orderEntity.setModificationDate(order.getUpdateTime());
    }
    if (order.getArchivedTime() != null) {
      orderEntity.setArchivedDate(order.getArchivedTime());
    }
    return orderEntity;
  }

  public static Order mapToDomain(OrderEntity entity) {
    return new Order(
        entity.getId(),
        UserMapper.toDomainFromEntity(entity.getUser()),
        entity.getProducts().stream()
            .map(product -> ProductMapper.toDomainFromEntity(product))
            .toList(),
        PaymentMapper.mapToDomain(entity.getPayment()),
        ShipmentMapper.mapToDomain(entity.getShipment()),
        entity.getCreationDate(),
        entity.getModificationDate(),
        entity.getArchivedDate());
  }

  public static ResultOrderDTO mapToResult(Order order) {
    ResultUserDTO user = UserMapper.toResultUserDTO(order.getUser());
    List<ResultProductDTO> products =
        order.getProducts().stream()
            .map(product -> ProductMapper.toResultFromDomain(product))
            .toList();
    return new ResultOrderDTO(
        order.getId().value(),
        user,
        order.getCreateTime().toString(),
        order.getUpdateTime().toString(),
        order.getPayment().getAmount().doubleValue(),
        products,
        order.getPayment().getPaymentMethod());
  }
}
