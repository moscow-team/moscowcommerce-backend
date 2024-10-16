package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "El email es requerido")
    @Size(min = 1, max = 50, message = "El email debe tener entre 1 y 50 caracteres")
    @Email(message = "El email no es válido")
    private String email;

    @NotBlank(message = "La contraseña existente es requerida")
    @Size(min = 8, max = 50, message = "La contraseña existente debe tener entre 8 y 50 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña existente debe tener al menos una letra mayúscula, una letra minúscula y un número")
    private String old_password;

    @NotBlank(message = "La nueva contraseña es requerida")
    @Size(min = 8, max = 50, message = "La nueva contraseña debe tener entre 8 y 50 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La nueva contraseña debe tener al menos una letra mayúscula, una letra minúscula y un número")
    private String new_password;
}
