package com.example.moscowcommerce_backend.Users.Application;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IUnarchiveUserService;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyUnarchived;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import org.springframework.stereotype.Service;

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
