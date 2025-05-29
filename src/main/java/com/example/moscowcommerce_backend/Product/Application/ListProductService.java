package com.example.moscowcommerce_backend.Product.Application;

import com.example.moscowcommerce_backend.Product.Application.interfaces.IListProductService;
import com.example.moscowcommerce_backend.Product.Domain.Exceptions.ProductNotFoundException;
import com.example.moscowcommerce_backend.Product.Domain.IProductRepository;
import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Product.Infrastructure.Mappers.ProductMapper;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ListProductService implements IListProductService {
  private final IProductRepository repository;

  public ListProductService(IProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<ProductEntity> findAll() {
    try {
      List<ProductEntity> productsEntity = repository.findAll();
      if (productsEntity.isEmpty()) {
        throw new ProductNotFoundException("No se encontraron productos");
      }

      return productsEntity;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Product findById(Integer id) {
    Optional<ProductEntity> product = repository.findById(id);

    if (product == null) {
      throw new RuntimeException("Product not found");
    }

    return ProductMapper.toDomainFromEntity(product.get());
  }

  @Override
  public List<Product> findByCriteria(Criteria criteria) {
    return repository.findByCriteria(criteria);
  }
}
