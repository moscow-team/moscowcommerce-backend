package com.example.moscowcommerce_backend.Product.Domain.Exceptions;

public class ProductAlreadyExist extends RuntimeException {
    public ProductAlreadyExist(String message) {
        super(message);
    }
}
