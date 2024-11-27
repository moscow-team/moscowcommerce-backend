package com.example.moscowcommerce_backend.Users.Domain;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository {
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(Integer id);
    public User save(User user);
    public List<User> findAll();
}
