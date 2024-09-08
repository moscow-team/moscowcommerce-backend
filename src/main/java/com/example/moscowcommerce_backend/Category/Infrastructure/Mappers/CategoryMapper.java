package com.example.moscowcommerce_backend.Category.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;;

public class CategoryMapper {
    public static Category toDomainFromDTO(CreateCategoryDTO categoryDTO) {
        return new Category(categoryDTO.name, categoryDTO.description);
    }

    public static Category toDomainFromEntity(Category categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription());
    }

    public static CategoryEntity toEntity(Category categoryDomain) {
        if (categoryDomain == null) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDomain.getId() != null ? categoryDomain.getId() : null);
        categoryEntity.setName(categoryDomain.getName());
        categoryEntity.setDescription(categoryDomain.getDescription());

        return categoryEntity;
    }

    public static ResultUserDTO toResultUserDTO(User userEntity) {
        return ResultUserDTO.create(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName());
    }
}
