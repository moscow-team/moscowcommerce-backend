package com.example.moscowcommerce_backend.Product.Application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Product.Application.interfaces.IListProductService;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;

@Service
public class ListProductService implements IListProductService {
    private  final IProductRepository repository;

    @Autowired
    public ListProductService(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productsEntity = this.repository.findAll();

        List<Product> products = productsEntity.stream().map(product -> ProductMapper.toDomainFromEntity(product)).toList();

        return products;
    } 

    @Override
    public Product findById(Integer id) {
        Optional<ProductEntity> product = this.repository.findById(id);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return ProductMapper.toDomainFromEntity(product.get());
    }
    
}
