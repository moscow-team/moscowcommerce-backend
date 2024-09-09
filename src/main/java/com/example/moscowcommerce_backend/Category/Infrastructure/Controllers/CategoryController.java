package com.example.moscowcommerce_backend.Category.Infrastructure.Controllers;

import com.example.moscowcommerce_backend.Category.Application.*;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.ResultCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.UpdateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CreateCategoryService createCategoryService;
    private final ListCategoryService listCategoryService;
    private final DeleteCategoryService deleteCategoryService;
    private final UnarchivedCategory unarchivedCategory;
    private final UpdateCategoryService updateCategoryService;
    @Autowired
    public CategoryController(
            CreateCategoryService createCategoryService,
            ListCategoryService listCategoryService,
            DeleteCategoryService deleteCategoryService,
            UnarchivedCategory unarchivedCategory,
            UpdateCategoryService updateCategoryService
    ) {
        this.createCategoryService = createCategoryService;
        this.listCategoryService = listCategoryService;
        this.deleteCategoryService = deleteCategoryService;
        this.unarchivedCategory = unarchivedCategory;
        this.updateCategoryService = updateCategoryService;
    }

    @PostMapping
    public ResponseEntity<Result<ResultCategoryDTO>> createCategory(@RequestBody CreateCategoryDTO category) {
        try {
            Category categoryToSave = CategoryMapper.toDomainFromDTO(category);
            CategoryEntity categorySaved = this.createCategoryService.create(categoryToSave);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromEntity(categorySaved);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria creada con éxito"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Result<List<ResultCategoryDTO>>> getAll() {
        try {
            List<CategoryEntity> categories = this.listCategoryService.execute();
            List<ResultCategoryDTO> categoriesDTO = categories.stream().map(category -> CategoryMapper.toResultFromEntity(category)).toList();
            return new ResponseEntity<>(Result.success(categoriesDTO, "Categorias obtenidas con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<ResultCategoryDTO>> deleteCategory(@PathVariable Integer id) {
        try {
            CategoryEntity category = this.deleteCategoryService.execute(id);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromEntity(category);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria eliminada con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<ResultCategoryDTO>> unarchivedCategory(@PathVariable Integer id) {
        try {
            CategoryEntity category = this.unarchivedCategory.execute(id);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromEntity(category);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria desarchivada con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Result<ResultCategoryDTO>> updateCategory(@RequestBody UpdateCategoryDTO category) {
        try {
            Category categoryToUpdate = CategoryMapper.toDomainFromDTO(category);
            CategoryEntity categoryUpdated = this.updateCategoryService.updateCategory(categoryToUpdate);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromEntity(categoryUpdated);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria actualizada con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
