package com.example.moscowcommerce_backend.Order.Infrastructure.Entities;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Entities.BaseEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
  private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
  private PaymentEntity payment;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "shipment_id", referencedColumnName = "id", nullable = false)
  private ShipmentEntity shipment;

  @Column(nullable = false, columnDefinition = "DATE", updatable = false)
  private LocalDate creationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate modificationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate archivedDate;

  @PrePersist
  protected void onCreate() {
    this.creationDate = LocalDate.now();
    this.modificationDate = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.modificationDate = LocalDate.now();
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "order_products",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<ProductEntity> products;
}
