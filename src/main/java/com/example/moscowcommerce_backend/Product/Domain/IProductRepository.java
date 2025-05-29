package com.example.moscowcommerce_backend.Product.Domain;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Domain.Ports.ICriteriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ICriteriaRepository<Product> {
  public List<ProductEntity> findAll();

  public Optional<ProductEntity> findById(Integer id);

  public Optional<ProductEntity> findByNameIgnoreCase(String name);

  public ProductEntity save(ProductEntity product);
}
