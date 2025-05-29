package com.example.moscowcommerce_backend.Users.Application;

import com.example.moscowcommerce_backend.Shared.Infrastructure.Utils;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.IUpdateUserService;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements IUpdateUserService {

  IUserRepository repository;

  @Autowired
  public UpdateUserService(IUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public ResultUserDTO update(User user) {

    Optional<User> existingUser = repository.findById(user.getId());

    if (!existingUser.isPresent()) {
      throw new UserNotFoundException(user.getId());
    }

    Utils.fillNullFields(user, existingUser.get());

    this.repository.save(user);

    ResultUserDTO userResult = UserMapper.toResultFromDomain(user);

    return userResult;
  }
}
