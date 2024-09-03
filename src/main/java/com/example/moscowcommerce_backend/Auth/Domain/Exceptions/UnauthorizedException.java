package com.example.moscowcommerce_backend.Auth.Domain.Exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){ super("Invalid credentials."); }

    public UnauthorizedException(String message) {
        super(message);
    }

    // Constructor con mensaje personalizado y causa
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
