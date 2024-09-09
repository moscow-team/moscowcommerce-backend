package com.example.moscowcommerce_backend.Category.Application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IUpdateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public class UpdateCategoryService implements IUpdateCategoryService {
    private final ICategoryRepository categoryRepository;

    @Autowired
    public UpdateCategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity updateCategory(Category category) {
        Optional<CategoryEntity> categoryEntity = this.categoryRepository.findById(category.getId());

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException("[Categoria: "+category.getName()+"]"+"La categoria no existe");
        }

        //TODO: Terminar de implementar la actualización de la categoría
        return categoryEntity.get();

    }


    
}
