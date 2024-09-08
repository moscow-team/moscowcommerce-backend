package com.example.moscowcommerce_backend.Category.Domain;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    public Optional<CategoryEntity[]>  getAll();
    public Optional<CategoryEntity> save(Category categoryDomain);
}
