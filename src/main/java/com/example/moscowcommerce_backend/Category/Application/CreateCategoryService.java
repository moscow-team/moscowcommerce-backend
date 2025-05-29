package com.example.moscowcommerce_backend.Category.Application;

import com.example.moscowcommerce_backend.Category.Application.Interfaces.ICreateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryAlreadyExist;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class CreateCategoryService implements ICreateCategoryService {
  private final ICategoryRepository repository;
  private final ListCategoryService listCategoryService;

  @Autowired
  public CreateCategoryService(
      ICategoryRepository repository, ListCategoryService listCategoryService) {
    this.repository = repository;
    this.listCategoryService = listCategoryService;
  }

  @Override
  public Category create(Category category) {
    Boolean existCategory = this.listCategoryService.existCategoryByName(category.getName());
    if (existCategory) {
      throw new CategoryAlreadyExist("Ya existe una categoría con el nombre " + category.getName());
    }

    return this.repository.save(category);
  }
}
