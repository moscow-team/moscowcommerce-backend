package com.example.moscowcommerce_backend.Product.Application.interfaces;

import java.util.List;

import com.example.moscowcommerce_backend.Product.Domain.Product;
import com.example.moscowcommerce_backend.Product.Infrastructure.Entities.ProductEntity;
import com.example.moscowcommerce_backend.Shared.Domain.Criteria.Criteria;

public interface IListProductService {
    public List<ProductEntity> findAll();
    public Product findById(Integer id);
    public List<Product> findByCriteria(Criteria criteria);
}
