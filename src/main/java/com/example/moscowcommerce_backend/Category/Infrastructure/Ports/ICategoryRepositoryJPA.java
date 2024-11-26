package com.example.moscowcommerce_backend.Category.Infrastructure.Ports;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public interface ICategoryRepositoryJPA extends JpaRepository<CategoryEntity, Integer> {
    public List<CategoryEntity>  findAll();
    public Optional<CategoryEntity> findByNameIgnoreCase(String name);
    public Optional<CategoryEntity> save(Category categoryDomain);
}
