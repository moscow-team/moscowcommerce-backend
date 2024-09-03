package com.example.moscowcommerce_backend.Auth.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Domain.User;

public interface IAuthService {
    boolean authenticate(User user);
}
