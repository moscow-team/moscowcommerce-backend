package com.example.moscowcommerce_backend.Users.Application;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IListUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListUserService implements IListUserService {
  private final IUserRepository repository;

  @Autowired
  public ListUserService(IUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<User> findAll() {
    List<User> users = this.repository.findAll();

    return users;
  }

  @Override
  public User findById(Integer id) {
    Optional<User> user = this.repository.findById(id);

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    return user.get();
  }

  @Override
  public User findByEmail(String email) {
    Optional<User> user = this.repository.findByEmail(email);

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    return user.get();
  }
}
