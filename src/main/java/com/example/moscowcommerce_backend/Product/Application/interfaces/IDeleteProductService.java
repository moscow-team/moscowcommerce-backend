package com.example.moscowcommerce_backend.Product.Application.interfaces;

import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;

public interface IDeleteProductService {
  public ProductEntity execute(Integer id);
}
