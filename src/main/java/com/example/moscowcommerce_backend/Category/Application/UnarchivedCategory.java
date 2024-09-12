package com.example.moscowcommerce_backend.Category.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IUnarchivedCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryIsNotArchived;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;

@Service
public class UnarchivedCategory implements IUnarchivedCategoryService{
    private final ICategoryRepository repository;

    @Autowired
    public UnarchivedCategory(ICategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryEntity execute(Integer id) {
        CategoryEntity categoryEntity = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Category category = CategoryMapper.toDomainFromEntity(categoryEntity);
        
        if (!category.isArchived()) {
            throw new CategoryIsNotArchived("La categpria no esta archivada");
        }

        category.unarchived();

        CategoryEntity categoryToUnarchive = CategoryMapper.toEntity(category);

        return repository.save(categoryToUnarchive);
    }
}
