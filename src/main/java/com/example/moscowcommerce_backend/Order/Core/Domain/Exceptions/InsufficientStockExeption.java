package com.example.moscowcommerce_backend.Order.Core.Domain.Exceptions;

public class InsufficientStockExeption extends RuntimeException {
    public InsufficientStockExeption(String message) {
        super(message);
    }   
}
