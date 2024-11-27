package com.example.moscowcommerce_backend.Users.Insfraestructure.Adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Ports.IUserRepositoryJPA;

@Repository
public class JPAUserRepository implements IUserRepository {
    private final IUserRepositoryJPA userRepositoryJPA;

    public JPAUserRepository(IUserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public User save(User user) {
        UserEntity userSaved = userRepositoryJPA.save(toEntity(user));

        return toDomain(userSaved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryJPA.findByEmail(email)
                .map(userEntity -> toDomain(userEntity));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepositoryJPA.findById(id)
                .map(userEntity -> toDomain(userEntity));
    }

    @Override
    public List<User> findAll() {
        return userRepositoryJPA.findAll().stream()
                .map(userEntity -> toDomain(userEntity))
                .toList();
    }

    private UserEntity toEntity(User user) {
        return UserMapper.toEntity(user);
    }

    private User toDomain(UserEntity userEntity) {
        return UserMapper.toDomainFromEntity(userEntity);
    }
}
