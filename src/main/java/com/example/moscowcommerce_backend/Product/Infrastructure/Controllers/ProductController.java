package com.example.moscowcommerce_backend.Product.Infrastructure.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Product.Application.CreateProductService;
import com.example.moscowcommerce_backend.Product.Application.ListProductService;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductService createProductService;
    private final ListProductService listProductService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(CreateProductService createProductService, ListProductService listProductService, ProductMapper productMapper) {
        this.createProductService = createProductService;
        this.listProductService = listProductService;
        this.productMapper = productMapper;
    }

    // Endpoint para crear un producto
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDTO product) {
        try {
            Product productToSave = productMapper.toDomainFromDTO(product);
            this.createProductService.create(productToSave);
            return new ResponseEntity<>("Product created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> listProduct() {
        try {
            List<Product> products = this.listProductService.findAll();

            List<ResultProductDTO> productsDTO = products.stream().map(product -> ProductMapper.toResultProductDTO(product)).toList();

            return new ResponseEntity<>(productsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
