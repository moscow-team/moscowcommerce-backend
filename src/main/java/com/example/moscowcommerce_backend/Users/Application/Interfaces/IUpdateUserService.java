package com.example.moscowcommerce_backend.Users.Application.Interfaces;

import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;

public interface IUpdateUserService {
  ResultUserDTO update(User user);
}
