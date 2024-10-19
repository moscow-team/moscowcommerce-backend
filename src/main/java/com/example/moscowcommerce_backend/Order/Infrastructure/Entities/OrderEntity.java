package com.example.moscowcommerce_backend.Order.Infrastructure.Entities;

import java.util.List;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Entities.BaseEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private PaymentEntity payment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id", nullable = false)
    private ShipmentEntity shipment;

    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;
}
