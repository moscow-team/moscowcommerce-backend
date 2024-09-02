package com.example.moscowcommerce_backend.Users.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moscowcommerce_backend.Users.Application.Interfaces.IListUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

@Service
public class ListUserService implements IListUserService{
    private final IUserRepository repository;

    @Autowired
    public ListUserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ResultUserDTO> findAll() {
        List<UserEntity> users = this.repository.findAll();

        List<ResultUserDTO> result = users.stream().map(user -> UserMapper.toResultUserDTO(user)).toList();

        return result;
    }
}
