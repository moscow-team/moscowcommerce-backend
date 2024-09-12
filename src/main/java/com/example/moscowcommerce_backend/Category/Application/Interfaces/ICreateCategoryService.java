package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public interface ICreateCategoryService {
    public CategoryEntity create(Category category);
}
