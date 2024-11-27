package com.example.moscowcommerce_backend.Users.Domain;

import java.time.LocalDate;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;

public final class User {
    private Integer id;
    private String email;
    private String fullName;
    private String password;
    private Role role;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private LocalDate archivedDate;

    public User() {
        id = null;
        email = null;
        fullName = null;
        password = null;
        role = null;
        this.creationDate = LocalDate.now();    
        this.modificationDate = LocalDate.now();
        this.archivedDate = null;
    }
    
    public User(Integer id, String email, String fullName, String password, Role rol) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = rol;
    }

    public User(Integer id, String email, String fullName, String password, Role rol, LocalDate creationDate, LocalDate modificationDate, LocalDate archivedDate) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = rol;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.archivedDate = archivedDate;
    }

    public User(String email, String fullName, String password, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.creationDate = LocalDate.now();    
        this.modificationDate = LocalDate.now();
        this.archivedDate = null;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public LocalDate getArchivedDate() {
        return archivedDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setArchivedDate(LocalDate archivedDate) {
        this.archivedDate = archivedDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean isArchived() {
        return this.archivedDate != null;
    }

    public void archive() {
        this.archivedDate = LocalDate.now();
    }

    public void unarchive() {
        this.archivedDate = null;
    }
}