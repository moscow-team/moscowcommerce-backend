package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    private Integer id;
    
    @Email(message = "El email no es valido")
    @Size(min = 5, max = 50, message = "El email debe tener entre 5 y 50 caracteres")
    private String email;
    
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String fullName;
    
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña debe tener al menos una letra mayúscula, una letra minúscula y un número")
    private String password;
    
    @Pattern(regexp = "^(CUSTOMER|ADMIN)$", message = "El rol debe ser CUSTOMER o ADMIN (mayúsculas)")
    private String role;
}
