package com.example.moscowcommerce_backend.Category.Application.Interfaces;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import java.util.List;

public interface IListCategoryService {
  public List<Category> execute();

  public Category findByName(String name);

  public Category findById(Integer id);

  public Boolean existCategoryByName(String name);

  public List<Category> findByCriteria(Criteria criteria);
}
