package com.example.moscowcommerce_backend.Category.Infrastructure.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "categories")
public final class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false, columnDefinition = "DATE", updatable = false)
    private LocalDate creationDate;

    @Column(nullable = true, columnDefinition = "DATE")
    private LocalDate modificationDate;

    @Column(nullable = true, columnDefinition = "DATE")
    private LocalDate archivedDate;

    // Método que se ejecuta antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
    }

    // Método que se ejecuta antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        this.modificationDate = LocalDate.now();
    }
}
