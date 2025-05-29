package com.example.moscowcommerce_backend.Category.Application;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IUnarchivedCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryIsNotArchived;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnarchivedCategory implements IUnarchivedCategoryService {
  private final ICategoryRepository repository;

  @Autowired
  public UnarchivedCategory(ICategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public Category execute(Integer id) {
    Category category =
        repository
            .findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

    if (!category.isArchived()) {
      throw new CategoryIsNotArchived("La categpria no esta archivada");
    }

    category.unarchived();

    return repository.save(category);
  }
}
