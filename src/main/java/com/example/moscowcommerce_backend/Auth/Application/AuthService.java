package com.example.moscowcommerce_backend.Auth.Application;

import com.example.moscowcommerce_backend.Auth.Application.Interfaces.IAuthService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(foundUser -> user.getPassword().equals(foundUser.getPassword()) )
                .orElse(false);
    }
}
