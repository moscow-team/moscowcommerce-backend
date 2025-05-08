package com.example.moscowcommerce_backend.Product.Domain;

import com.example.moscowcommerce_backend.Category.Domain.Category;
import com.example.moscowcommerce_backend.Shared.Domain.PriceValueObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private Category category;
    private PriceValueObject price;
    private Integer stock;
    private List<ProductPhoto> urlPhotos = new ArrayList<>();
    private LocalDate archivedDate;

    public Product(){
        id = null;
        name = null;
        description = null;
        category = null;
        price = new PriceValueObject(BigDecimal.ZERO);
        stock = 0;
        urlPhotos = new ArrayList<>();
        this.archivedDate = null;
    }

    public Product(String name, String description, Integer stock, Double price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = new PriceValueObject(BigDecimal.valueOf(price));
    }

    public Product(String name, String description, Category category, Double price, Integer stock, List<ProductPhoto> urlPhotos) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = new PriceValueObject(BigDecimal.valueOf(price));
        this.stock = stock;
        this.urlPhotos = urlPhotos;
    }

    public Product(Integer id, String name, String description, Category category, Double price, Integer stock, List<ProductPhoto> urlPhotos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = new PriceValueObject(BigDecimal.valueOf(price));
        this.stock = stock;
        this.urlPhotos = urlPhotos;
    }

    public Product(Integer id, String name, String description, Category category, Double price, Integer stock, List<ProductPhoto> urlPhotos, LocalDate archivedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = new PriceValueObject(BigDecimal.valueOf(price));
        this.stock = stock;
        this.urlPhotos = urlPhotos;
        this.archivedDate = archivedDate;
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
        return price.getValue().doubleValue();
    }

    public void setPrice(double price) {
        this.price = new PriceValueObject(BigDecimal.valueOf(price));
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

    public LocalDate getArchivedDate() {
        return archivedDate;
    }

    public void archived() {
        this.archivedDate = LocalDate.now();
    }

    public boolean isArchived() {
        return this.archivedDate != null;
    }

    public void unarchived() {
        this.archivedDate = null;
    }
}