package com.example.moscowcommerce_backend.Category.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IDeleteCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryAlreadyArchived;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;

@Service
public class DeleteCategoryService implements IDeleteCategoryService {

    private final ICategoryRepository repository;

    @Autowired
    public DeleteCategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public CategoryEntity execute(Integer id) {
        CategoryEntity categoryEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        Category category = CategoryMapper.toDomainFromEntity(categoryEntity);

        if (category.isArchived()) {
            throw new CategoryAlreadyArchived("La categoria ya esta archivada");
        }

        category.archived();

        return repository.save(CategoryMapper.toEntity(category));
    }
    
}
