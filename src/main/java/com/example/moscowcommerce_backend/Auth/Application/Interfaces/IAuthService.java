package com.example.moscowcommerce_backend.Auth.Application.Interfaces;

import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;

public interface IAuthService {
  UserEntity authenticate(LogInDTO logInDTO);
}
