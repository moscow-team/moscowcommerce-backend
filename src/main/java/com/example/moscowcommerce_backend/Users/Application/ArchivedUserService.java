package com.example.moscowcommerce_backend.Users.Application;

import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IArchivedUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyArchived;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;

@Service
public class ArchivedUserService implements IArchivedUserService{
    IUserRepository userRepository;

    public ArchivedUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User archive(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (user.isArchived()) {
            throw new UserAlreadyArchived(id);
        }

        user.archive();

        return userRepository.save(user);
    }
}
