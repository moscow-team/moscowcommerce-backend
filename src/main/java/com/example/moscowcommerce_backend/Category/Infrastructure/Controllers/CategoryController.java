package com.example.moscowcommerce_backend.Category.Infrastructure.Controllers;

import com.example.moscowcommerce_backend.Category.Application.*;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.*;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.ResultCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.UpdateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Filter;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Utils;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Result<ResultCategoryDTO>> createCategory(@Valid @RequestBody CreateCategoryDTO category) {
        try {
            Category categoryToSave = CategoryMapper.toDomainFromDTO(category);
            Category categorySaved = this.createCategoryService.create(categoryToSave);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromDomain(categorySaved);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria creada con éxito"), HttpStatus.CREATED);
        } catch (CategoryAlreadyExist e) { // Capturamos la excepción personalizada
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Result<List<ResultCategoryDTO>>> getAll() {
        try {
            List<Category> categories = this.listCategoryService.execute();
            List<ResultCategoryDTO> categoriesDTO = categories.stream().map(category -> CategoryMapper.toResultFromDomain(category)).toList();
            return new ResponseEntity<>(Result.success(categoriesDTO, "Categorias obtenidas con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<ResultCategoryDTO>> deleteCategory(@PathVariable Integer id) {
        try {
            Category category = this.deleteCategoryService.execute(id);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromDomain(category);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria eliminada con éxito"), HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<ResultCategoryDTO>> unarchivedCategory(@PathVariable Integer id) {
        try {
            Category category = this.unarchivedCategory.execute(id);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromDomain(category);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria desarchivada con éxito"), HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CategoryIsNotArchived e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<ResultCategoryDTO>> updateCategory(@RequestBody UpdateCategoryDTO category, @PathVariable Integer id) {
        try {
            Category categoryToUpdate = CategoryMapper.toDomainFromUpdateDTO(category, id);
            Category categoryUpdated = this.updateCategoryService.updateCategory(categoryToUpdate);
            ResultCategoryDTO categoryDTO = CategoryMapper.toResultFromDomain(categoryUpdated);
            return new ResponseEntity<>(Result.success(categoryDTO, "Categoria actualizada con éxito"), HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get by Criteria
    @GetMapping("/filters")
    public ResponseEntity<Result<List<ResultCategoryDTO>>> getByCriteria(@RequestParam Map<String, String> params) {
        try {
            // Validar los parámetros de entrada
            if (params.containsKey("invalidParam")) {
                return new ResponseEntity<>(Result.failure("Invalid parameter: invalidParam"), HttpStatus.BAD_REQUEST);
            }

            List<Filter> filters = Utils.getFiltersFromParams(params);
            Criteria criteria = Criteria.create(filters);

            List<Category> categories = this.listCategoryService.findByCriteria(criteria);

            List<ResultCategoryDTO> categoriesDTO = categories.stream().map(category -> CategoryMapper.toResultFromDomain(category)).toList();
            return new ResponseEntity<>(Result.success(categoriesDTO, "Categorias obtenidas con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
