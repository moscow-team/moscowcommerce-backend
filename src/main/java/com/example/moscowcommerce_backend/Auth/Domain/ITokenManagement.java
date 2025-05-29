package com.example.moscowcommerce_backend.Auth.Domain;

import com.example.moscowcommerce_backend.Users.Domain.User;

public interface ITokenManagement {
  String generateToken(User user);

  boolean isTokenValid(String token, String username);

  String extractEmailFromToken(String token);
}
