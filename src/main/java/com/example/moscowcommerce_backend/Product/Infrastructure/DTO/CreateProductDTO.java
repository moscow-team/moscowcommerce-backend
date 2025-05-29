package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductDTO {
  @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
  public String name;

  @Size(min = 1, max = 254, message = "La descripción debe tener entre 1 y 254 caracteres")
  public String description;

  public Integer stock;

  @DecimalMin(value = "0.01", message = "El precio minimo es 0.01")
  @DecimalMax(value = "9999.99", message = "El precio maximo es 9999.99")
  public Double price;

  public String categoryId;
  public List<String> urlPhotos = new ArrayList<>();
  public List<MultipartFile> photos;
}
