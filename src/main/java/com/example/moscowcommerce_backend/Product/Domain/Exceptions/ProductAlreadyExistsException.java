package com.example.moscowcommerce_backend.Product.Domain.Exceptions;

public class ProductAlreadyExistsException extends RuntimeException 
{
    public ProductAlreadyExistsException() {
        super("Product already exists.");
    }
    
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
