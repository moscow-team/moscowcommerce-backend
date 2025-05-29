package com.example.moscowcommerce_backend.Category.Domain.Exceptions;

public class CategoryIsNotArchived extends RuntimeException {
  public CategoryIsNotArchived(String message) {
    super(message);
  }
}
