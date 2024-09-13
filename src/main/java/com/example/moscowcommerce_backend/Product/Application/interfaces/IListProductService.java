package com.example.moscowcommerce_backend.Product.Application.interfaces;

import java.util.List;

import com.example.moscowcommerce_backend.Product.Domain.Product;

public interface IListProductService {
    List<Product> findAll();
    Product findById(Integer id);
}
