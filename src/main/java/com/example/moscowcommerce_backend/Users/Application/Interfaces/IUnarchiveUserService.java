package com.example.moscowcommerce_backend.Users.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Domain.User;

public interface IUnarchiveUserService {
  User unarchive(Integer id);
}
