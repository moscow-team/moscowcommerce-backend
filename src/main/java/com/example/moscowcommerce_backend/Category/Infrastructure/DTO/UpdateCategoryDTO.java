package com.example.moscowcommerce_backend.Category.Infrastructure.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCategoryDTO {

    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres")
    private String description;
}
