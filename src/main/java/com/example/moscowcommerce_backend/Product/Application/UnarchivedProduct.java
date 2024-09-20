package com.example.moscowcommerce_backend.Product.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Product.Application.interfaces.IUnarchivedProductService;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductIsNotArchived;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductNotFoundException;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;


@Service
public class UnarchivedProduct implements IUnarchivedProductService{
    private final IProductRepository repository;

    @Autowired
    public UnarchivedProduct(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductEntity execute(Integer id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product not found")
        );

        Product product = ProductMapper.toDomainFromEntity(productEntity);

        if (!product.isArchived()) {
            throw new ProductIsNotArchived("El producto no está archivado.");
        }

        product.unarchived();
        
        // Mantener las fotos existentes
        ProductEntity productToUnarchive = ProductMapper.toEntity(product);
        productToUnarchive.setPhotos(productEntity.getPhotos()); // Mantener fotos

        return repository.save(productToUnarchive);
    }

}
