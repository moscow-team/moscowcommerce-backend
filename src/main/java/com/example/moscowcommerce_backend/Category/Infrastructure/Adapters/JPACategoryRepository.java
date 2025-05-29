package com.example.moscowcommerce_backend.Category.Infrastructure.Adapters;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Category.Infrastructure.Ports.ICategoryRepositoryJPA;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Adapters.CriteriaRepositoryAdapter;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class JPACategoryRepository extends CriteriaRepositoryAdapter<CategoryEntity, Category>
    implements ICategoryRepository {
  private final ICategoryRepositoryJPA categoryRepository;

  public JPACategoryRepository(
      ICategoryRepositoryJPA categoryRepository, EntityManager entityManager) {
    super(entityManager);
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll().stream()
        .map(entity -> toDomain(entity))
        .collect(Collectors.toList());
  }

  protected Category toDomain(CategoryEntity entity) {
    return CategoryMapper.toDomainFromEntity(entity);
  }

  protected CategoryEntity toEntity(Category domain) {
    return CategoryMapper.toEntity(domain);
  }

  public Optional<Category> findByNameIgnoreCase(String name) {
    return categoryRepository.findByNameIgnoreCase(name).map(entity -> toDomain(entity));
  }

  public Category save(Category categoryDomain) {
    CategoryEntity categoryEntity = toEntity(categoryDomain);
    return toDomain(categoryRepository.save(categoryEntity));
  }

  public Optional<Category> findById(Integer id) {
    return categoryRepository.findById(id).map(entity -> toDomain(entity));
  }

  @Override
  public List<Category> findByCriteria(Criteria criteria) {
    return super.findByCriteria(criteria, CategoryEntity.class);
  }
}
