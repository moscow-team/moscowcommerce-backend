package com.example.moscowcommerce_backend.Product.Infrastructure.DTO;

public class ResultProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer categoryId;

    public ResultProductDTO(Integer id, String name, String description, Double price, Integer stock, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
