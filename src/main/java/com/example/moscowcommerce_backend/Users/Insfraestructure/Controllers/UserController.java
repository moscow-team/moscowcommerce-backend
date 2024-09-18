package com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Application.ListUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import jakarta.validation.Valid;

import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserService createUserService;
    private final ListUserService listUserService;
    private final PasswordEncoder passwordEncoder;

    // Inyección del servicio mediante el constructor
    @Autowired
    public UserController(
        CreateUserService createUserService,
        ListUserService listUserService,
        PasswordEncoder passwordEncoder
        ) {
        this.createUserService = createUserService;
        this.listUserService = listUserService;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint para crear un usuario
    @PostMapping
    public ResponseEntity<Result<ResultUserDTO>> createUser(@Valid @RequestBody CreateUserDTO user) {
        try {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            User userToSave = UserMapper.toDomainFromDTO(user);
            ResultUserDTO result = this.createUserService.create(userToSave);
            return new ResponseEntity<>(Result.success(result, "Usuario creado con éxito"), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Result<List<ResultUserDTO>>> listUser() {
        try {
            List<User> users = this.listUserService.findAll();

            List<ResultUserDTO> result = users.stream().map(user -> UserMapper.toResultUserDTO(user)).toList();
            return new ResponseEntity<>(Result.success(result, "Usuarios listados con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

