package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import com.example.moscowcommerce_backend.Category.Domain.Category;

public interface IDeleteCategoryService {
    public Category execute(Integer id);
}
