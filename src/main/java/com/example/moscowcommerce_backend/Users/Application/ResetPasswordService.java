package com.example.moscowcommerce_backend.Users.Application;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IResetPassword;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResetPasswordDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService implements IResetPassword {
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ResetPasswordService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ResultUserDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
    String email = resetPasswordDTO.getEmail();

    Optional<User> userExists = this.userRepository.findByEmail(email);

    User userEntity = userExists.orElseThrow(() -> new UserNotFoundException(email));

    userEntity.setPassword(this.passwordEncoder.encode("AaBbCc123"));

    User userSaved = this.userRepository.save(userEntity);

    ResultUserDTO userDTO = UserMapper.toResultFromDomain(userSaved);

    return userDTO;
  }
}
