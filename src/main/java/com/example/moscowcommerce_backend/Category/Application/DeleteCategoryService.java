package com.example.moscowcommerce_backend.Category.Application;

import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IDeleteCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryAlreadyArchived;

@Service
public class DeleteCategoryService implements IDeleteCategoryService {

    private final ICategoryRepository repository;

    @Autowired
    public DeleteCategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Category execute(Integer id) {
        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (category.isArchived()) {
            throw new CategoryAlreadyArchived("La categoria ya esta archivada");
        }

        category.archived();

        return repository.save(category);
    }
    
}
