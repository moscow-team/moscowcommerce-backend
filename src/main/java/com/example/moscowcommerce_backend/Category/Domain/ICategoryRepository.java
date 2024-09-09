package com.example.moscowcommerce_backend.Category.Domain;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    public List<CategoryEntity>  findAll();
    public Optional<CategoryEntity> save(Category categoryDomain);
}
