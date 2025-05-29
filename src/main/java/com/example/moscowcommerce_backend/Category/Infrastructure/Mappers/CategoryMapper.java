package com.example.moscowcommerce_backend.Category.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.ResultCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.UpdateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

public class CategoryMapper {
  public static Category toDomainFromDTO(CreateCategoryDTO categoryDTO) {
    return new Category(categoryDTO.getName(), categoryDTO.getDescription());
  }

  public static Category toDomainFromEntity(CategoryEntity categoryEntity) {
    if (categoryEntity == null) {
      return null;
    }

    if (categoryEntity.getArchivedDate() != null) {
      return new Category(
          categoryEntity.getId(),
          categoryEntity.getName(),
          categoryEntity.getDescription(),
          categoryEntity.getCreationDate(),
          categoryEntity.getModificationDate(),
          categoryEntity.getArchivedDate());
    }

    return new Category(
        categoryEntity.getId(),
        categoryEntity.getName(),
        categoryEntity.getDescription(),
        categoryEntity.getCreationDate(),
        categoryEntity.getModificationDate());
  }

  public static CategoryEntity toEntity(Category categoryDomain) {
    if (categoryDomain == null) {
      return null;
    }

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setId(categoryDomain.getId() != null ? categoryDomain.getId() : null);
    categoryEntity.setName(categoryDomain.getName());
    categoryEntity.setDescription(categoryDomain.getDescription());
    categoryEntity.setCreationDate(categoryDomain.getCreationDate());
    categoryEntity.setModificationDate(categoryDomain.getModificationDate());

    if (categoryDomain.getArchivedDate() != null) {
      categoryEntity.setArchivedDate(categoryDomain.getArchivedDate());
    }

    return categoryEntity;
  }

  public static ResultCategoryDTO toResultFromEntity(CategoryEntity categoryEntity) {
    if (categoryEntity == null) {
      return null;
    }

    String archivedDate =
        categoryEntity.getArchivedDate() != null ? categoryEntity.getArchivedDate().toString() : "";
    String creationDate =
        categoryEntity.getCreationDate() != null ? categoryEntity.getCreationDate().toString() : "";
    String modificationDate =
        categoryEntity.getModificationDate() != null
            ? categoryEntity.getModificationDate().toString()
            : "";

    // Determinar si el producto está archivado
    boolean archived = categoryEntity.getArchivedDate() != null;

    return ResultCategoryDTO.create(
        categoryEntity.getId(),
        categoryEntity.getName(),
        categoryEntity.getDescription(),
        creationDate,
        modificationDate,
        archived,
        archivedDate);
  }

  public static ResultCategoryDTO toResultFromDomain(Category category) {
    if (category == null) {
      return null;
    }

    String archivedDate =
        category.getArchivedDate() != null ? category.getArchivedDate().toString() : "";
    String creationDate =
        category.getCreationDate() != null ? category.getCreationDate().toString() : "";
    String modificationDate =
        category.getModificationDate() != null ? category.getModificationDate().toString() : "";

    // Determinar si el producto está archivado
    boolean archived = category.getArchivedDate() != null;

    return ResultCategoryDTO.create(
        category.getId(),
        category.getName(),
        category.getDescription(),
        creationDate,
        modificationDate,
        archived,
        archivedDate);
  }

  public static Category toDomainFromUpdateDTO(UpdateCategoryDTO categoryDTO, Integer id) {
    return new Category(id, categoryDTO.getName(), categoryDTO.getDescription());
  }
}
