package com.example.moscowcommerce_backend.Category.Infrastructure.Entities;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "categories")
public final class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = true)
  private String description;

  @Column(nullable = false, columnDefinition = "DATE", updatable = false)
  private LocalDate creationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate modificationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate archivedDate;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductEntity> products = new ArrayList<>();

  @PrePersist
  protected void onCreate() {
    this.creationDate = LocalDate.now();
    this.modificationDate = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.modificationDate = LocalDate.now();
  }
}
