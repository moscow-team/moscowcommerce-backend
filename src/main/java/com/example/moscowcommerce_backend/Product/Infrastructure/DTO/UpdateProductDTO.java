package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProductDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    public String categoryId;
    public List<String> urlPhotos = new ArrayList<>(); // Lista para fotos nuevas
    public List<MultipartFile> photos;
    public List<String> existingPhotos = new ArrayList<>(); // Lista para fotos existentes
    public List<String> photosToDelete = new ArrayList<>(); // Lista para fotos a eliminar
}
