package com.example.moscowcommerce_backend.Users.Domain.Exceptions;

public class PasswordDoesNotMatchException extends RuntimeException{
    public PasswordDoesNotMatchException(String oldPassword){
        super("La contraseña " + oldPassword + " no coincide con la del usuario");
    }
}
