package com.example.moscowcommerce_backend.Auth.Infrastructure.Mappers;

import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.ResultLogInDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;

public class LogInMapper {
  public static ResultLogInDTO toResult(UserEntity user, String token) {
    return new ResultLogInDTO(
        token, user.getEmail(), user.getFullName(), user.getId(), user.getRole());
  }
}
