package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import java.util.List;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public interface IListCategoryService {
   public List<CategoryEntity> execute(); 
   public CategoryEntity findByName(String name);
   public CategoryEntity findById(Integer id);
   public Boolean existCategoryByName(String name);
}
