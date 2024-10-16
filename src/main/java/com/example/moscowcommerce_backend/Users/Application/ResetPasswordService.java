package com.example.moscowcommerce_backend.Users.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IResetPassword;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.PasswordDoesNotMatchException;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResetPasswordDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import java.util.Optional;

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
        String oldPassword = resetPasswordDTO.getOld_password();
        String newPassword = resetPasswordDTO.getNew_password();

        Optional<UserEntity> userExists = this.userRepository.findByEmail(email);

        UserEntity userEntity = userExists.orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new PasswordDoesNotMatchException(oldPassword);
        }

        userEntity.setPassword(this.passwordEncoder.encode(newPassword));

        this.userRepository.save(userEntity);

        ResultUserDTO userDTO = UserMapper.toResultFromEntity(userEntity);

        return userDTO;
    }
}
