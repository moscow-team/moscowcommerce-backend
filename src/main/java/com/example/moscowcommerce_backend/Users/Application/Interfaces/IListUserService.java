package com.example.moscowcommerce_backend.Users.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Domain.User;
import java.util.List;

public interface IListUserService {
  List<User> findAll();

  User findById(Integer id);

  User findByEmail(String email);
}
