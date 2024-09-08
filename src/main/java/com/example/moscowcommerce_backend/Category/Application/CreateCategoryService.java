package com.example.moscowcommerce_backend.Category.Application;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.ICreateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;


public final class CreateCategoryService implements ICreateCategoryService {
    private final ICategoryRepository repository;

    @Autowired
    public CreateCategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Category category) {
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category);

        System.out.println(categoryEntity);
        this.repository.save(categoryEntity);
    }
}
