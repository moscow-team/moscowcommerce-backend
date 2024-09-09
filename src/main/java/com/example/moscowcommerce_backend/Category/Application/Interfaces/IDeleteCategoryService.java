package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public interface IDeleteCategoryService {
    public CategoryEntity execute(Integer id);
}
