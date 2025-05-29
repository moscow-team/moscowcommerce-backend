package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import com.example.moscowcommerce_backend.Category.Domain.Category;

public interface ICreateCategoryService {
  public Category create(Category category);
}
