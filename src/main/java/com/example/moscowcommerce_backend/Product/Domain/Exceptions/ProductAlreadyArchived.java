package com.example.moscowcommerce_backend.Product.Domain.Exceptions;

public class ProductAlreadyArchived extends RuntimeException {
    public ProductAlreadyArchived(String message) {
        super(message);
    }
}
