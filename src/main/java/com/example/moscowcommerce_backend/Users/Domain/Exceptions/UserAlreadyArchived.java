package com.example.moscowcommerce_backend.Users.Domain.Exceptions;

public class UserAlreadyArchived extends RuntimeException {
    public UserAlreadyArchived(Integer id) {
        super("User with id " + id + " is already archived");
    }
}
