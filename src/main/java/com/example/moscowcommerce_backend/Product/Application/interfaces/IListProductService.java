package com.example.moscowcommerce_backend.Product.Application.interfaces;

import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;
import java.util.List;

public interface IListProductService {
  public List<ProductEntity> findAll();

  public Product findById(Integer id);

  public List<Product> findByCriteria(Criteria criteria);
}
