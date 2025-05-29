package com.example.moscowcommerce_backend.Product.Domain.Exceptions;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
}
