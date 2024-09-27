package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductDTO {
    public String name;
    public String description;
    public Integer stock;
    public Double price;
    public String categoryId;
    public List<String> urlPhotos = new ArrayList<>();
    public List<MultipartFile> photos;
}
