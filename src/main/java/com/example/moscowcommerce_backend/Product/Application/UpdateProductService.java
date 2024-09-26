package com.example.moscowcommerce_backend.Product.Application;

import com.example.moscowcommerce_backend.Category.Domain.Category;
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

        String name = updateProduct.getName() != null ? updateProduct.getName() : existingProduct.getName();
        String description = updateProduct.getDescription() != null ? updateProduct.getDescription() : existingProduct.getDescription();
        Integer stock = updateProduct.getStock() > 0 ? updateProduct.getStock() : existingProduct.getStock();
        Double price = updateProduct.getPrice() > 0 ? updateProduct.getPrice() : existingProduct.getPrice();
        Category category  = updateProduct.getCategory() != null ? updateProduct.getCategory() : CategoryMapper.toDomainFromEntity(existingProduct.getCategory());

        // Actualizar los atributos del producto existente
        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setStock(stock);
        existingProduct.setCategory(CategoryMapper.toEntity(category));

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
