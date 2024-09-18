package com.example.moscowcommerce_backend.Users.Domain;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;

public final class User {
    private Integer id;
    private String email;
    private String fullName;
    private String password;
    private Role role;

    public User() {
        id = null;
        email = null;
        fullName = null;
        password = null;
        role = null;
    }

    public User(Integer id, String email, String fullName, String password, Role rol) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String email, String fullName, String password, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
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
}