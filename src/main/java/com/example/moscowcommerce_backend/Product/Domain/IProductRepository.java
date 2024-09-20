package com.example.moscowcommerce_backend.Product.Domain;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {
    public List<ProductEntity> findAll();
    public Optional<ProductEntity> findById(Integer id);
    public Optional<ProductEntity> findByNameIgnoreCase(String name);
    public Optional<ProductEntity> save(Product productDomain);
}
