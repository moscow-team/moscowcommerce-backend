package com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;

import java.time.LocalDateTime;
import java.util.Date;

public class UserMapper {
    public static User toDomain(CreateUserDTO userDTO) {
       return new User(userDTO.email, userDTO.fullName, userDTO.password);
    }

    public static UserEntity toEntity(User userDomain) {
        if (userDomain == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId() != null ? userDomain.getId() : null);
        userEntity.setEmail(userDomain.getEmail());
        userEntity.setFullName(userDomain.getFullName());
        userEntity.setPassword(userDomain.getPassword());

        return userEntity;
    }
}
