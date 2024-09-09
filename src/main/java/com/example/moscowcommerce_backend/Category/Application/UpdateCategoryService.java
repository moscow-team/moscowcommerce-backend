package com.example.moscowcommerce_backend.Category.Application;

import java.util.Optional;

import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IUpdateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryService implements IUpdateCategoryService {
    private final ICategoryRepository categoryRepository;

    @Autowired
    public UpdateCategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity updateCategory(Category updateCategory) {
        Optional<CategoryEntity> categoryEntity = this.categoryRepository.findById(updateCategory.getId());

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException("[Categoria: "+updateCategory.getName()+"]"+"La categoria no existe");
        }

        //TODO: Terminar de implementar la actualización de la categoría
        CategoryEntity category = CategoryMapper.toEntity(updateCategory);

        return categoryRepository.save(category);

    }


    
}
