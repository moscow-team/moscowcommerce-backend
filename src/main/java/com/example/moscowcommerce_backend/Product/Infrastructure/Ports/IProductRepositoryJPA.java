package com.example.moscowcommerce_backend.Product.Infrastructure.Ports;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepositoryJPA extends JpaRepository<ProductEntity, Integer> {
  public List<ProductEntity> findAll();

  public Optional<ProductEntity> findById(Integer id);

  public Optional<ProductEntity> findByNameIgnoreCase(String name);

  @SuppressWarnings("unchecked")
  public ProductEntity save(ProductEntity product);
}
