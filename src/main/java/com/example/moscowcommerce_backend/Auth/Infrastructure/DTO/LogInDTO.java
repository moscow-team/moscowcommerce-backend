package com.example.moscowcommerce_backend.Auth.Infrastructure.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LogInDTO {
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    public String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    public String password;

    public LogInDTO() {
    }

    public LogInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
