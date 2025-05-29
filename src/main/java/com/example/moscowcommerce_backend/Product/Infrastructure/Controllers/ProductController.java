package com.example.moscowcommerce_backend.Product.Infrastructure.Controllers;

import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Product.Application.CreateProductService;
import com.example.moscowcommerce_backend.Product.Application.DeleteProductService;
import com.example.moscowcommerce_backend.Product.Application.ListProductService;
import com.example.moscowcommerce_backend.Product.Application.UnarchivedProduct;
import com.example.moscowcommerce_backend.Product.Application.UpdateProductService;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductIsNotArchived;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductNotFoundException;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.UpdateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Shared.Application.StorageImage;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Filter;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Utils;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final CreateProductService createProductService;
  private final ListProductService listProductService;
  private final UpdateProductService updateProductService;
  private final ICategoryRepository categoryRepository;
  private final DeleteProductService deleteProductService;
  private final UnarchivedProduct unarchivedProduct;
  private final StorageImage storageImage;

  public ProductController(
      CreateProductService createProductService,
      ListProductService listProductService,
      UpdateProductService updateProductService,
      ICategoryRepository categoryRepository,
      DeleteProductService deleteProductService,
      UnarchivedProduct unarchivedProduct,
      StorageImage storageImage) {
    this.createProductService = createProductService;
    this.listProductService = listProductService;
    this.updateProductService = updateProductService;
    this.categoryRepository = categoryRepository;
    this.deleteProductService = deleteProductService;
    this.unarchivedProduct = unarchivedProduct;
    this.storageImage = storageImage;
  }

  // Endpoint para crear un producto
  @PostMapping(value = "", consumes = "multipart/form-data")
  public ResponseEntity<Result<ResultProductDTO>> createProduct(
      @Valid @ModelAttribute CreateProductDTO product) {
    try {
      if (product.getPhotos() == null || product.getPhotos().isEmpty()) {
        product.setUrlPhotos(Collections.emptyList());
      } else {
        var storageResult = storageImage.HandleImage(product.getPhotos());
        product.setUrlPhotos(storageResult);
      }
      product.setPhotos(null);
      Product productToSave = ProductMapper.toDomainFromDTO(product, categoryRepository);
      ProductEntity productSaved = this.createProductService.create(productToSave);
      ResultProductDTO productDTO = ProductMapper.toResultFromEntity(productSaved);
      return new ResponseEntity<>(
          Result.success(productDTO, "Producto creado con éxito."), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<Result<List<ResultProductDTO>>> listProduct() {
    try {
      List<ProductEntity> products = this.listProductService.findAll();
      List<ResultProductDTO> productsDTO =
          products.stream().map(product -> ProductMapper.toResultFromEntity(product)).toList();
      return new ResponseEntity<>(
          Result.success(productsDTO, "Productos obtenidos con exito."), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Result<ResultProductDTO>> deleteProduct(@PathVariable Integer id) {
    try {
      ProductEntity product = this.deleteProductService.execute(id);
      ResultProductDTO productDTO = ProductMapper.toResultFromEntity(product);
      return new ResponseEntity<>(
          Result.success(productDTO, "Producto eliminado con exito."), HttpStatus.OK);
    } catch (ProductNotFoundException e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Result<ResultProductDTO>> unarchivedProduct(@PathVariable Integer id) {
    try {
      ProductEntity product = this.unarchivedProduct.execute(id);
      ResultProductDTO productDTO = ProductMapper.toResultFromEntity(product);
      return new ResponseEntity<>(
          Result.success(productDTO, "Producto desarchivado con exito."), HttpStatus.OK);
    } catch (ProductNotFoundException e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (ProductIsNotArchived e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(value = "/{id}", consumes = "multipart/form-data")
  public ResponseEntity<Result<ResultProductDTO>> updateProduct(
      @ModelAttribute UpdateProductDTO product, @PathVariable Integer id) {
    try {
      if (product.getPhotos() == null || product.getPhotos().isEmpty()) {
        product.setUrlPhotos(Collections.emptyList());
      } else {
        var storageResult = storageImage.HandleImage(product.getPhotos());
        product.setUrlPhotos(storageResult);
      }
      Product productToUpdate =
          ProductMapper.toDomainFromUpdateDTO(product, id, categoryRepository);
      ProductEntity productUpdated = this.updateProductService.updateProduct(productToUpdate);
      ResultProductDTO productDTO = ProductMapper.toResultFromEntity(productUpdated);
      return new ResponseEntity<>(
          Result.success(productDTO, "Producto actualizado con exito."), HttpStatus.OK);
    } catch (ProductNotFoundException e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Get By Criteria
  @GetMapping("/filters")
  public ResponseEntity<Result<List<ResultProductDTO>>> getProductsByFilters(
      @RequestParam Map<String, String> params) {
    try {
      List<Filter> filters = Utils.getFiltersFromParams(params);
      Criteria criteria = Criteria.create(filters);

      List<Product> products = this.listProductService.findByCriteria(criteria);

      List<ResultProductDTO> productsDTO =
          products.stream().map(product -> ProductMapper.toResultFromDomain(product)).toList();

      return new ResponseEntity<>(
          Result.success(productsDTO, "Productos obtenidos con exito."), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
