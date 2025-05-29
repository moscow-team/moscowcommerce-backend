package com.example.moscowcommerce_backend.Auth.Domain;

import com.example.moscowcommerce_backend.Users.Domain.User;

public interface ILogin {
  String login(User user);
}
