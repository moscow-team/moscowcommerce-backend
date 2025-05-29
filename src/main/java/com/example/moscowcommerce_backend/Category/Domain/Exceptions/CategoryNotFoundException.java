package com.example.moscowcommerce_backend.Category.Domain.Exceptions;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(String message) {
    super(message);
  }
}
