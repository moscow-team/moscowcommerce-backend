package com.example.moscowcommerce_backend.Category.Domain;

import java.time.LocalDate;

public class Category {
  private Integer id;
  private String name;
  private String description;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private LocalDate archivedDate;

  public Category() {
    this.id = null;
    this.name = null;
    this.description = null;
    this.creationDate = null;
    this.modificationDate = null;
    this.archivedDate = null;
  }

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Category(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Category(
      Integer id,
      String name,
      String description,
      LocalDate creationDate,
      LocalDate modificationDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.creationDate = creationDate;
    this.modificationDate = modificationDate;
  }

  public Category(
      Integer id,
      String name,
      String description,
      LocalDate creationDate,
      LocalDate modificationDate,
      LocalDate archivedDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.creationDate = creationDate;
    this.modificationDate = modificationDate;
    this.archivedDate = archivedDate;
  }

  public Integer getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
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
