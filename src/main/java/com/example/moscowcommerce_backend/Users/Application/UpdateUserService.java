package com.example.moscowcommerce_backend.Users.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Shared.Infrastructure.Utils;
import com.example.moscowcommerce_backend.Users.Application.Interfaces.IUpdateUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import java.util.Optional;;

@Service
public class UpdateUserService implements IUpdateUserService {

    IUserRepository repository;

    @Autowired
    public UpdateUserService(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ResultUserDTO update(User user) {
        
        Optional<UserEntity> existingUser = repository.findById(user.getId());

        if (!existingUser.isPresent()) {
           throw new UserNotFoundException(user.getId());
        }

        UserEntity userEntity = UserMapper.toEntity(user);

        Utils.fillNullFields(userEntity, existingUser.get());

        System.out.println("email " + userEntity.getEmail());
        
        this.repository.save(userEntity);

        ResultUserDTO userResult = UserMapper.toResultFromEntity(userEntity);

        return userResult;
    }
}
