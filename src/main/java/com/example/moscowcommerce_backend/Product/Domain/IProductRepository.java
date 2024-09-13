package com.example.moscowcommerce_backend.Product.Domain;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {
    public Optional<ProductEntity> findById(Integer id);
}
