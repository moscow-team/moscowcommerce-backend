package com.example.moscowcommerce_backend.Users.Domain.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {
  // Constructor sin parámetros
  public UserAlreadyExistsException() {
    super("El usuario ya existe.");
  }

  // Constructor con un mensaje personalizado
  public UserAlreadyExistsException(String message) {
    super(message);
  }

  // Constructor con mensaje personalizado y causa
  public UserAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor con causa
  public UserAlreadyExistsException(Throwable cause) {
    super(cause);
  }
}
