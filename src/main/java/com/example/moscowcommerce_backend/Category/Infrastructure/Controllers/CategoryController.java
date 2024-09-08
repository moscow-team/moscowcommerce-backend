package com.example.moscowcommerce_backend.Category.Infrastructure.Controllers;

import com.example.moscowcommerce_backend.Category.Application.CreateCategoryService;
import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Infrastructure.DTO.CreateCategoryDTO;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CreateCategoryService createCategoryService;
    // Inyección del servicio mediante el constructor
    @Autowired
    public CategoryController(
            CreateCategoryService createCategoryService
    ) {
        this.createCategoryService = createCategoryService;
    }

    // Endpoint para crear un Categoria
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryDTO category) {
        try {
            Category categoryToSave = CategoryMapper.toDomainFromDTO(category);
            this.createCategoryService.create(categoryToSave);
            return new ResponseEntity<>("Category created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
