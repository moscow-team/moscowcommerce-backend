package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

  @NotBlank(message = "El nombre completo es requerido")
  @Size(max = 50, message = "El nombre completo debe tener entre 1 y 50 caracteres")
  @Pattern(
      regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
      message = "El nombre completo solo puede contener letras y espacios")
  private String fullName;

  @NotBlank(message = "El email es requerido")
  @Size(max = 50, message = "El email debe tener entre 1 y 50 caracteres")
  @Email(message = "El email no es válido")
  private String email;

  @NotBlank(message = "El rol es requerido")
  @Pattern(regexp = "^(CUSTOMER|ADMIN)$", message = "El rol debe ser CUSTOMER o ADMIN (mayúsculas)")
  private String role;

  @NotBlank(message = "La contraseña es requerida")
  @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
      message =
          "La contraseña debe tener al menos una letra mayúscula, una letra minúscula y un número")
  private String password;

  public CreateUserDTO(String fullName, String email, String password, String role) {
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public CreateUserDTO() {}
}
