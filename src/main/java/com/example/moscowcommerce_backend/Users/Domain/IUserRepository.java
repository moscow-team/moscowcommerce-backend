package com.example.moscowcommerce_backend.Users.Domain;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository {
  public Optional<User> findByEmail(String email);

  public Optional<User> findById(Integer id);

  public User save(User user);

  public List<User> findAll();
}
