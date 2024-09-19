package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

import java.util.ArrayList;
import java.util.List;

public class CreateProductDTO {
    public String name;
    public String description;
    public Integer stock;
    public Double price;
    public Integer categoryId;
    public List<String> urlPhotos = new ArrayList<>();
}
