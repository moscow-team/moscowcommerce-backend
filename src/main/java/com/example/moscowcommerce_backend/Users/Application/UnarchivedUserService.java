package com.example.moscowcommerce_backend.Users.Application;

import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IUnarchiveUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyUnarchived;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;

@Service
public class UnarchivedUserService implements IUnarchiveUserService {

    private final IUserRepository userRepository;

    public UnarchivedUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User unarchive(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!user.isArchived()) {
            throw new UserAlreadyUnarchived(id);
        }

        user.unarchive();
        return userRepository.save(user);
    }
    
}
