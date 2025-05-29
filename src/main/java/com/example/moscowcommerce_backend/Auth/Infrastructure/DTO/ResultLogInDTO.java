package com.example.moscowcommerce_backend.Auth.Infrastructure.DTO;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;
import lombok.Data;

@Data
public class ResultLogInDTO {
  private String token;
  private Number userId;
  private String email;
  private String fullName;
  private Role role;

  public ResultLogInDTO(String token, String email, String fullName, Number userId, Role role) {
    this.token = token;
    this.email = email;
    this.fullName = fullName;
    this.userId = userId;
    this.role = role;
  }
}
