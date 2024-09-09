package com.example.moscowcommerce_backend.Category.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IListCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

@Service
public class ListCategoryService implements IListCategoryService {
    
    ICategoryRepository categoryRepository;

    @Autowired
    public ListCategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> execute() {
        try {
            List<CategoryEntity> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                throw new CategoryNotFoundException("No se encontraron categorias");
            }

            return categories;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
