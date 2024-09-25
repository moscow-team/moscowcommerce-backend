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

    @Override
    public CategoryEntity findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CategoryNotFoundException("No se encontró la categoría " + name));
    }

    @Override
    public CategoryEntity findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No se encontró la categoría con id " + id));
    }

    @Override
    public Boolean existCategoryByName(String name) {
        CategoryEntity category = this.categoryRepository.findByNameIgnoreCase(name).orElse(null);
        return category != null;
    }
}
