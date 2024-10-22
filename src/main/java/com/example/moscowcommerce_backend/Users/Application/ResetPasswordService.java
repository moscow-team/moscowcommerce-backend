package com.example.moscowcommerce_backend.Users.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IResetPassword;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.ISendEmail;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.PasswordDoesNotMatchException;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import java.util.Optional;

@Service
public class ResetPasswordService implements IResetPassword {
    private final IUserRepository userRepository;
    private final ISendEmail sendEmail;

    @Autowired
    public ResetPasswordService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResultUserDTO resetPassword(String email) {

        this.userRepository.save(userEntity);

        ResultUserDTO userDTO = UserMapper.toResultFromEntity(userEntity);

        return userDTO;
    }
}
