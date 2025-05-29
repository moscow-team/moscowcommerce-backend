package com.example.moscowcommerce_backend.Product.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.ProductPhoto;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.ResultProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.UpdateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductPhotoEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  // Mapea un dominio de producto a una entidad sin incluir las fotos.
  public static ProductEntity toEntity(Product productDomain) {
    if (productDomain == null) {
      return null;
    }

    ProductEntity productEntity = new ProductEntity();

    if (productDomain.getId() != null) {
      productEntity.setId(productDomain.getId());
    }

    productEntity.setName(productDomain.getName());
    productEntity.setDescription(productDomain.getDescription());
    productEntity.setPrice(productDomain.getPrice());
    productEntity.setStock(productDomain.getStock());
    productEntity.setCategory(CategoryMapper.toEntity(productDomain.getCategory()));
    productEntity.setPhotos(
        productDomain.getPhotos().stream()
            .map(photo -> toPhotoEntity(photo, productEntity))
            .collect(Collectors.toList()));

    if (productDomain.getArchivedDate() != null) {
      productEntity.setArchivedDate(productDomain.getArchivedDate());
    }

    return productEntity;
  }

  // Agrega las fotos al ProductEntity.
  public static void addPhotosToProductEntity(Product product, ProductEntity productEntity) {
    if (productEntity.getPhotos() == null) {
      productEntity.setPhotos(new ArrayList<>());
    }

    List<ProductPhotoEntity> photoEntities =
        product.getPhotos().stream()
            .map(photo -> toPhotoEntity(photo, productEntity))
            .collect(Collectors.toList());

    productEntity.getPhotos().addAll(photoEntities);
  }

  public static ProductPhotoEntity toPhotoEntity(ProductPhoto photo, ProductEntity productEntity) {
    ProductPhotoEntity photoEntity = new ProductPhotoEntity();
    photoEntity.setUrl(photo.getUrlPhoto());
    photoEntity.setProduct(productEntity);
    return photoEntity;
  }

  public static Product toDomainFromDTO(
      CreateProductDTO productDTO, ICategoryRepository categoryRepository) {
    Category categoryDomain = null;

    if (productDTO.getCategoryId() != null) {
      Optional<Category> categoryOptional =
          categoryRepository.findById(Integer.valueOf(productDTO.getCategoryId()));
      if (categoryOptional.isEmpty()) {
        throw new CategoryNotFoundException(
            "Category with ID " + productDTO.getCategoryId() + " not found.");
      }
      CategoryEntity categoryEntity = CategoryMapper.toEntity(categoryOptional.get());
      categoryDomain = CategoryMapper.toDomainFromEntity(categoryEntity);
    }

    List<ProductPhoto> photos =
        productDTO.urlPhotos.stream()
            .map(url -> new ProductPhoto(url))
            .collect(Collectors.toList());

    return new Product(
        productDTO.name,
        productDTO.description,
        categoryDomain,
        productDTO.price,
        productDTO.stock,
        photos);
  }

  public static Product toDomainFromEntity(ProductEntity productEntity) {
    if (productEntity == null) {
      return null;
    }

    Category category =
        productEntity.getCategory() != null
            ? CategoryMapper.toDomainFromEntity(productEntity.getCategory())
            : null;

    List<ProductPhoto> photos =
        productEntity.getPhotos() != null
            ? productEntity.getPhotos().stream()
                .map(photo -> new ProductPhoto(photo.getId(), photo.getUrl()))
                .collect(Collectors.toList())
            : Collections.emptyList();

    return new Product(
        productEntity.getId(),
        productEntity.getName(),
        productEntity.getDescription(),
        category,
        productEntity.getPrice(),
        productEntity.getStock(),
        photos,
        productEntity.getArchivedDate());
  }

  public static ResultProductDTO toResultFromEntity(ProductEntity productEntity) {
    if (productEntity == null) {
      return null;
    }

    String price = String.valueOf(productEntity.getPrice());
    String stock = String.valueOf(productEntity.getStock());

    // Obtener el objeto Category
    Category category = CategoryMapper.toDomainFromEntity(productEntity.getCategory());

    List<String> photoUrls =
        productEntity.getPhotos().stream()
            .map(ProductPhotoEntity::getUrl)
            .collect(Collectors.toList());

    String archivedDate =
        productEntity.getArchivedDate() != null ? productEntity.getArchivedDate().toString() : "";

    // Determinar si el producto está archivado
    boolean archived = productEntity.getArchivedDate() != null;

    return ResultProductDTO.create(
        productEntity.getId(),
        productEntity.getName(),
        productEntity.getDescription(),
        price,
        stock,
        category,
        photoUrls,
        archived,
        archivedDate);
  }

  public static ResultProductDTO toResultFromDomain(Product productDomain) {
    if (productDomain == null) {
      return null;
    }

    String price = String.valueOf(productDomain.getPrice());
    String stock = String.valueOf(productDomain.getStock());

    // Obtener el objeto Category
    Category category = productDomain.getCategory();

    List<String> photoUrls =
        productDomain.getPhotos().stream()
            .map(ProductPhoto::getUrlPhoto)
            .collect(Collectors.toList());

    String archivedDate =
        productDomain.getArchivedDate() != null ? productDomain.getArchivedDate().toString() : "";

    // Determinar si el producto está archivado
    boolean archived = productDomain.getArchivedDate() != null;

    return ResultProductDTO.create(
        productDomain.getId(),
        productDomain.getName(),
        productDomain.getDescription(),
        price,
        stock,
        category,
        photoUrls,
        archived,
        archivedDate);
  }

  public static Product toDomainFromUpdateDTO(
      UpdateProductDTO productDTO, Integer id, ICategoryRepository categoryRepository) {
    Category categoryDomain = null;

    if (productDTO.getCategoryId() != null) {
      Optional<Category> categoryOptional =
          categoryRepository.findById(Integer.valueOf(productDTO.getCategoryId()));
      if (categoryOptional.isEmpty()) {
        throw new CategoryNotFoundException(
            "Category with ID " + productDTO.getCategoryId() + " not found.");
      }

      CategoryEntity categoryEntity = CategoryMapper.toEntity(categoryOptional.get());
      categoryDomain = CategoryMapper.toDomainFromEntity(categoryEntity);
    }

    List<ProductPhoto> photos = new ArrayList<>();

    // Agregar fotos existentes
    if (productDTO.getExistingPhotos() != null) {
      photos.addAll(
          productDTO.getExistingPhotos().stream()
              .map(url -> new ProductPhoto(url))
              .collect(Collectors.toList()));
    }

    // Agregar nuevas fotos
    if (productDTO.getUrlPhotos() != null) {
      photos.addAll(
          productDTO.getUrlPhotos().stream()
              .map(url -> new ProductPhoto(url))
              .collect(Collectors.toList()));
    }

    // Agregar fotos a eliminar
    if (productDTO.getPhotosToDelete() != null) {
      for (String photoUrl : productDTO.getPhotosToDelete()) {
        photos.removeIf(photo -> photo.getUrlPhoto().equals(photoUrl));
      }
    }

    return new Product(
        id,
        productDTO.getName(),
        productDTO.getDescription(),
        categoryDomain,
        productDTO.getPrice(),
        productDTO.getStock(),
        photos);
  }
}
