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
    public List<String> urlPhotos = new ArrayList<>();
    public List<MultipartFile> photos;
}
