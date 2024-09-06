package com.example.moscowcommerce_backend.Users.Application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IListUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

@Service
public class ListUserService implements IListUserService {
    private final IUserRepository repository;

    @Autowired
    public ListUserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> usersEntity = this.repository.findAll();

        List<User> users = usersEntity.stream().map(user -> UserMapper.toDomainFromEntity(user)).toList();

        return users;
    }

    @Override
    public User findById(Integer id) {
        Optional<UserEntity> user = this.repository.findById(id);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return UserMapper.toDomainFromEntity(user.get());
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> user = this.repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return UserMapper.toDomainFromEntity(user.get());
    }
}
