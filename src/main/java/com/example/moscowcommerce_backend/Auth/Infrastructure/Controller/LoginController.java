package com.example.moscowcommerce_backend.Auth.Infrastructure.Controller;

import com.example.moscowcommerce_backend.Auth.Application.AuthService;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Security.JwtService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public LoginController(AuthService authenticate, JwtService jwtService) {
        this.jwtService = jwtService;
        this.authService = authenticate;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LogInDTO logInDTO) {
        try {
            UserEntity user = authService.authenticate(logInDTO);
            if(user == null) {
                return "Email o contraseña incorrectos";
            } 

            String jwtToken = this.jwtService.generateToken(user);

            return jwtToken;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
