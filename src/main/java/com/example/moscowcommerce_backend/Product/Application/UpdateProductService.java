package com.example.moscowcommerce_backend.Product.Application;

import com.example.moscowcommerce_backend.Category.Infrastructure.Mappers.CategoryMapper;
import com.example.moscowcommerce_backend.Product.Application.interfaces.IUpdateProductService;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductNotFoundException;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductPhotoEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UpdateProductService implements IUpdateProductService {
    private final IProductRepository productRepository;
    
    public UpdateProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
        
    }
    @Override
    public ProductEntity updateProduct(Product updateProduct) {
        // Obtener el producto existente desde la base de datos.
        Optional<ProductEntity> existingProductOpt = this.productRepository.findById(updateProduct.getId());
        if (existingProductOpt.isEmpty()) {
            throw new ProductNotFoundException(
                "[Producto: " + updateProduct.getName() + "] El producto no existe");
        }

        ProductEntity existingProduct = existingProductOpt.get();

        // Actualizar los atributos del producto existente
        existingProduct.setName(updateProduct.getName());
        existingProduct.setDescription(updateProduct.getDescription());
        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setStock(updateProduct.getStock());
        existingProduct.setCategory(CategoryMapper.toEntity(updateProduct.getCategory()));

        // Limpiar las fotos existentes y agregar las nuevas solo si se proporcionan
        if (updateProduct.getPhotos() != null && !updateProduct.getPhotos().isEmpty()) {
            existingProduct.getPhotos().clear();
            List<ProductPhotoEntity> updatedPhotos = updateProduct.getPhotos().stream()
                .map(photo -> ProductMapper.toPhotoEntity(photo, existingProduct))
                .collect(Collectors.toList());
            existingProduct.getPhotos().addAll(updatedPhotos);
        }
        
        // Guardar el producto actualizado.
        return this.productRepository.save(existingProduct);
    }
    
}
