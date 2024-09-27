package com.example.moscowcommerce_backend.Product.Infrastructure.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.moscowcommerce_backend.Category.Infrastructure.Entities.CategoryEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "product")
public final class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPhotoEntity> photos = new ArrayList<>();

    @Column(nullable = true, columnDefinition = "DATE")
    private LocalDate archivedDate;

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void addPhoto(ProductPhotoEntity photo) {
        this.photos.add(photo);
    }

    public void removePhoto(ProductPhotoEntity photo) {
        this.photos.remove(photo);
    } 
}