package com.example.moscowcommerce_backend.Category.Domain.Exceptions;

public class CategoryAlreadyExist extends RuntimeException {
  public CategoryAlreadyExist(String message) {
    super(message);
  }
}
