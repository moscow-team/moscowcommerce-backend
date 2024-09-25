package com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.UpdateUserDTO;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Utils;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;

public class UserMapper {
    public static User toDomainFromDTO(CreateUserDTO userDTO) {
        Role role = Role.valueOf(userDTO.getRole().toUpperCase());
        return new User(userDTO.getEmail(), userDTO.getFullName(), userDTO.getPassword(), role);
    }

    public static User toDomainFromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new User(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getPassword(), userEntity.getRole());
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
        userEntity.setRole(userDomain.getRole());

        return userEntity;
    }

    public static ResultUserDTO toResultUserDTO(User user) {
        return ResultUserDTO.create(user.getId(), user.getEmail(), user.getFullName(), user.getRole());
    }

    public static ResultUserDTO toResultFromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return ResultUserDTO.create(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getRole());
    }

    public static User updateDTOToUserDomain(UpdateUserDTO userDTO) {
        Role role = null;
        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            role = Role.valueOf(userDTO.getRole().toUpperCase());
        }
        User user = new User(userDTO.getId(), userDTO.getEmail(), userDTO.getFullName(), userDTO.getPassword(), role);

        Utils.removeNullFields(user);

        System.out.println(user.getEmail());

        return user;
    }
}
