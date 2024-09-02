package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import lombok.Data;

@Data
public class ResultUserDTO {
    private Integer id;
    private String email;
    private String fullName;

    private ResultUserDTO(Integer id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public static ResultUserDTO create(Integer id, String email, String fullName) {
        return new ResultUserDTO(id, email, fullName);
    }
}
