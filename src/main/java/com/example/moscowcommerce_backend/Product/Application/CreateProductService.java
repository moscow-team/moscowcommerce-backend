package com.example.moscowcommerce_backend.Product.Application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Product.Application.interfaces.ICreateProductService;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductAlreadyExistsException;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;

@Service
public class CreateProductService implements ICreateProductService {
    private final IProductRepository productRepository;

    public CreateProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void create(Product product) {
        // Esta validacion se podria obviar ya que la base de datos no permite generar
        // dos usuarios con el mismo email,
        // de esta forma manejamos nosotros la excepcion
        // Lo hacemos verificando si existe un producto con este id, y si existe
        // devolvemos la excepcion.
        if (product.getId() != null) {
            Optional<ProductEntity> productExist = this.productRepository.findById(product.getId());
            if (productExist.isPresent()) {
                throw new ProductAlreadyExistsException();
            }
        }
        // Primero debemos crear el producto sin las fotos ya que no podriamos
        // referenciar las fotos a un producto no creado
        ProductEntity productEntity = ProductMapper.toEntityWithoutPhotos(product);
        productEntity = productRepository.save(productEntity);
        // Luego de crear el producto, podemos agregar las fotos al producto
        ProductMapper.addPhotosToProductEntity(product, productEntity);
        // Y guardamos el producto con las fotos
        productRepository.save(productEntity);
    }

}
