package com.example.moscowcommerce_backend.Auth.Infrastructure.DTO;

public class ResultLogInDTO {
    private String token;
    private Number userId;
    private String email;
    private String fullName;


    public ResultLogInDTO(String token, String email, String fullName, Number userId) {
        this.token = token;
        this.email = email;
        this.fullName = fullName;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Number getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
