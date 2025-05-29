package com.example.moscowcommerce_backend.Shared.Domain.Exceptions;

public class InvalidOperatorException extends RuntimeException {
  public InvalidOperatorException(String message) {
    super(message);
  }
}
