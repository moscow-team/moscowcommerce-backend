package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriteriaParams {

  @NotBlank(message = "Field no puede estar vacio en el parametro de busqueda")
  @Size(min = 1, max = 50)
  private String field;

  private String value;
  private String operator;
}
