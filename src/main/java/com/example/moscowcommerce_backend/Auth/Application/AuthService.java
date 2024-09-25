package com.example.moscowcommerce_backend.Auth.Application;

import com.example.moscowcommerce_backend.Auth.Application.Interfaces.IAuthService;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Users.Application.ListUserService;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.IListUserService;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final IListUserService listUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(ListUserService listUserService, AuthenticationManager authenticationManager) {
        this.listUserService = listUserService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserEntity authenticate(@Valid LogInDTO logInDTO) {
        try {
            User user = this.listUserService.findByEmail(logInDTO.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDTO.getEmail(),
                            logInDTO.getPassword()));

            return UserMapper.toEntity(user);
        } catch (Exception e) {
            return null;
        }
    }
}
