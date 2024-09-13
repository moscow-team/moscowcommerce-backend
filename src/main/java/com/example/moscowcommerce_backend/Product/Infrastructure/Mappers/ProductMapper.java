package com.example.moscowcommerce_backend.Product.Infrastructure.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Category.Domain.ICategoryRepository;
import com.example.moscowcommerce_backend.Category.Domain.Exceptions.CategoryNotFoundException;
import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;
import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.ProductPhoto;
import com.example.moscowcommerce_backend.Product.Infrastructure.DTO.CreateProductDTO;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductPhotoEntity;

public class ProductMapper {

    private final ICategoryRepository categoryRepository;

    public ProductMapper(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static ProductEntity toEntityWithoutPhotos(Product product) {
        if (product == null) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setStock(product.getStock());
        productEntity.setCategory(CategoryMapper.toEntity(product.getCategory()));

        return productEntity;
    }

    public static void addPhotosToProductEntity(Product product, ProductEntity productEntity) {
        List<ProductPhotoEntity> photoEntities = product.getPhotos().stream()
                .map(photo -> toPhotoEntity(photo, productEntity))
                .collect(Collectors.toList());
        productEntity.setPhotos(photoEntities);
    }
    
    private static ProductPhotoEntity toPhotoEntity(ProductPhoto photo, ProductEntity productEntity) {
        ProductPhotoEntity photoEntity = new ProductPhotoEntity();
        photoEntity.setUrl(photo.getUrlPhoto());
        photoEntity.setProduct(productEntity);
        return photoEntity;
    }

    public Product toDomainFromDTO(CreateProductDTO productDTO) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(productDTO.categoryId);
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category not found");
        }
        CategoryEntity categoryEntity = categoryOptional.get();
        Category categoryDomain = CategoryMapper.toDomainFromEntity(categoryEntity);

        List<ProductPhoto> photos = productDTO.urlPhotos.stream()
                .map(url -> new ProductPhoto(url))
                .collect(Collectors.toList());

        return new Product(
            productDTO.name,
            productDTO.description,
            categoryDomain,
            productDTO.price,
            productDTO.stock,
            photos
        );
        
    }
}
