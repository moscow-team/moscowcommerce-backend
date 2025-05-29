package com.example.moscowcommerce_backend.Shared.Infrastructure.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public abstract class BaseEntity {
  @Column(nullable = false, columnDefinition = "DATE", updatable = false)
  private LocalDate creationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate modificationDate;

  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate archivedDate;

  @PrePersist
  protected void onCreate() {
    this.creationDate = LocalDate.now();
    this.modificationDate = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.modificationDate = LocalDate.now();
  }
}
