package com.example.moscowcommerce_backend.Product.Application.interfaces;

import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;

public interface ICreateProductService {
  public ProductEntity create(Product product);
}
