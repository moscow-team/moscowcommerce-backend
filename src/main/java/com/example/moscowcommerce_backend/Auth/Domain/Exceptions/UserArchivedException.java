package com.example.moscowcommerce_backend.Auth.Domain.Exceptions;

public class UserArchivedException extends RuntimeException {
    public UserArchivedException(String email) {
        super("User with email " + email + " is archived");
    }
    
}
