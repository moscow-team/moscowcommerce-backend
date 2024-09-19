package com.example.moscowcommerce_backend.Product.Domain;

import com.example.moscowcommerce_backend.Category.Domain.Category;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private Category category;
    private Double price;
    private Integer stock;
    private List<ProductPhoto> urlPhotos = new ArrayList<>();

    public Product(){
        id = null;
        name = null;
        description = null;
        category = null;
        price = 0.0;
        stock = 0;
        urlPhotos = null;
    }

    public Product(String name, String description, Integer stock, Double price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public Product(String name, String description, Category category, Double price, Integer stock, List<ProductPhoto> urlPhotos) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.urlPhotos = urlPhotos;
    }

    public Product(Integer id, String name, String description, Category category, Double price, Integer stock, List<ProductPhoto> urlPhotos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.urlPhotos = urlPhotos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<ProductPhoto> getPhotos() {
        return urlPhotos;
    }

    public void setPhotos(List<ProductPhoto> urlPhotos) {
        this.urlPhotos = urlPhotos;
    }
}