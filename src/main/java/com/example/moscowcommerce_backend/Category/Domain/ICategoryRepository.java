package com.example.moscowcommerce_backend.Category.Domain;

import com.example.moscowcommerce_backend.Shared.Domain.Ports.ICriteriaRepository;
import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends ICriteriaRepository<Category> {
  public List<Category> findAll();

  public Optional<Category> findByNameIgnoreCase(String name);

  public Category save(Category categoryDomain);

  public Optional<Category> findById(Integer id);
}
