package com.example.moscowcommerce_backend.Category.Domain.Exceptions;

public class CategoryAlreadyArchived extends RuntimeException {
  public CategoryAlreadyArchived(String message) {
    super(message);
  }
}
