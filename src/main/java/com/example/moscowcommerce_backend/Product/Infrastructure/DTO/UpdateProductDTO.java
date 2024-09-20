package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class UpdateProductDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    private Integer categoryId;
    private List<String> urlPhotos = new ArrayList<>();
}
