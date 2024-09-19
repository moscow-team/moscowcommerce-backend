package com.example.moscowcommerce_backend.Product.Domain.Exceptions;

public class ProductIsNotArchived extends RuntimeException {
    public ProductIsNotArchived(String message) {
        super(message);
    }
    
}
