package com.example.moscowcommerce_backend.Product.Infrastructure.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Product.Application.CreateProductService;
import com.example.moscowcommerce_backend.Product.Application.DeleteProductService;
import com.example.moscowcommerce_backend.Product.Application.ListProductService;
import com.example.moscowcommerce_backend.Product.Application.UnarchivedProduct;
import com.example.moscowcommerce_backend.Product.Application.UpdateProductService;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.UpdateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductService createProductService;
    private final ListProductService listProductService;
    private final UpdateProductService updateProductService;
    private final ICategoryRepository categoryRepository;
    private final DeleteProductService deleteProductService;
    private final UnarchivedProduct unarchivedProduct;

    public ProductController(
            CreateProductService createProductService, 
            ListProductService listProductService, 
            UpdateProductService updateProductService,
            ICategoryRepository categoryRepository,
            DeleteProductService deleteProductService,
            UnarchivedProduct unarchivedProduct
    ) {
        this.createProductService = createProductService;
        this.listProductService = listProductService;
        this.updateProductService = updateProductService;
        this.categoryRepository = categoryRepository;
        this.deleteProductService = deleteProductService;
        this.unarchivedProduct = unarchivedProduct;
    }

    // Endpoint para crear un producto
    @PostMapping
    public ResponseEntity<Result<ResultProductDTO>> createProduct(@RequestBody CreateProductDTO product) {
        System.out.println("CreateProductDTO received: " + product);
        try {
            Product productToSave = ProductMapper.toDomainFromDTO(product, categoryRepository);
            ProductEntity productSaved = this.createProductService.create(productToSave);
            ResultProductDTO productDTO = ProductMapper.toResultFromEntity(productSaved);
            return new ResponseEntity<>(Result.success(productDTO, "Producto creado con éxito."), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Result<List<ResultProductDTO>>> listProduct() {
        try {
            List<ProductEntity> products = this.listProductService.findAll();
            List<ResultProductDTO> productsDTO = products.stream().map(product -> ProductMapper.toResultFromEntity(product)).toList();
            return new ResponseEntity<>(Result.success(productsDTO, "Productos obtenidos con exito."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<ResultProductDTO>> deleteProduct(@PathVariable Integer id) {
        try {
            ProductEntity product = this.deleteProductService.execute(id);
            ResultProductDTO productDTO = ProductMapper.toResultFromEntity(product);
            return new ResponseEntity<>(Result.success(productDTO, "Producto eliminado con exito."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<ResultProductDTO>> unarchivedProduct(@PathVariable Integer id) {
        try {
            ProductEntity product = this.unarchivedProduct.execute(id);
            ResultProductDTO productDTO = ProductMapper.toResultFromEntity(product);
            return new ResponseEntity<>(Result.success(productDTO, "Producto desarchivado con exito."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<ResultProductDTO>> updateProduct(@RequestBody UpdateProductDTO product, @PathVariable Integer id) {
        try {
            Product productToUpdate = ProductMapper.toDomainFromUpdateDTO(product, id, categoryRepository);
            ProductEntity productUpdated = this.updateProductService.updateProduct(productToUpdate);
            ResultProductDTO productDTO = ProductMapper.toResultFromEntity(productUpdated);
            return new ResponseEntity<>(Result.success(productDTO, "Producto actualizado con exito."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
