package com.example.moscowcommerce_backend.Users.Domain.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("Usuario no encontrado con el id: " + id);
    }
}
