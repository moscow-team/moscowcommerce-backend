package com.example.moscowcommerce_backend.Users.Insfraestructure.DTO;

import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;

import lombok.Data;

@Data
public class ResultUserDTO {
    private Integer id;
    private String email;
    private String fullName;
    private Role role;

    private ResultUserDTO(Integer id, String email, String fullName, Role role) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role= role;
    }

    public static ResultUserDTO create(Integer id, String email, String fullName, Role role) {
        return new ResultUserDTO(id, email, fullName, role);
    }
}
