package com.example.moscowcommerce_backend.Product.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Product.Application.interfaces.IDeleteProductService;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductAlreadyArchived;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;

@Service
public class DeleteProductService implements IDeleteProductService {

    private final IProductRepository repository;

    @Autowired
    public DeleteProductService(IProductRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ProductEntity execute(Integer id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        Product product = ProductMapper.toDomainFromEntity(productEntity);

        if (product.isArchived()) {
            throw new ProductAlreadyArchived("El producto ya está archivado.");
        }

        product.archived();

        // Mantener las fotos existentes
        ProductEntity updatedProductEntity = ProductMapper.toEntity(product);
        updatedProductEntity.setPhotos(productEntity.getPhotos()); // Mantener fotos

        return repository.save(updatedProductEntity);
    }
    
}
