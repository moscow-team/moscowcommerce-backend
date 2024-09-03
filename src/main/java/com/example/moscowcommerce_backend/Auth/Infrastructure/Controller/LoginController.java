package com.example.moscowcommerce_backend.Auth.Infrastructure.Controller;

import com.example.moscowcommerce_backend.Auth.Application.AuthService;
import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.UnauthorizedException;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Security.TokenManagement;
import com.example.moscowcommerce_backend.Users.Domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final AuthService authService;
    private final TokenManagement tokenManagement;

    public LoginController(AuthService authenticate, TokenManagement tokenManagement) {
        this.authService = authenticate;
        this.tokenManagement = tokenManagement;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        if(authService.authenticate(user)){
            return tokenManagement.generateToken(user);
        }else {
            throw new UnauthorizedException();
        }
    }
}
