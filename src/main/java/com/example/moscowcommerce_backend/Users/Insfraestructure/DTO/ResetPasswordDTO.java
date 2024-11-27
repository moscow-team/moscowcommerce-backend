package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "El email es requerido")
    @Size(min = 1, max = 50, message = "El email debe tener entre 1 y 50 caracteres")
    @Email(message = "El email no es válido")
    private String email;
}
