package com.example.moscowcommerce_backend.Category.Application;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.IUpdateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryService implements IUpdateCategoryService {
  private final ICategoryRepository categoryRepository;

  @Autowired
  public UpdateCategoryService(ICategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category updateCategory(Category updateCategory) {
    Optional<Category> categoryEntity = this.categoryRepository.findById(updateCategory.getId());

    if (categoryEntity.isEmpty()) {
      throw new CategoryNotFoundException(
          "[Categoria: " + updateCategory.getName() + "]" + "La categoria no existe");
    }

    return categoryRepository.save(updateCategory);
  }
}
