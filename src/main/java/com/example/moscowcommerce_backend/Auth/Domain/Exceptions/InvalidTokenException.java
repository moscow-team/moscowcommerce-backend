package com.example.moscowcommerce_backend.Auth.Domain.Exceptions;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String message) {
    super(message);
  }
}
