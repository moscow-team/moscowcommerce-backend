package com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Application.ListUserService;
import com.example.moscowcommerce_backend.Users.Application.UpdateUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.UpdateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;

import jakarta.validation.Valid;

import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserNotFoundException;
import com.example.moscowcommerce_backend.Users.Domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserService createUserService;
    private final ListUserService listUserService;
    private final PasswordEncoder passwordEncoder;
    private final UpdateUserService updateUserService;

    // Inyección del servicio mediante el constructor
    @Autowired
    public UserController(
        CreateUserService createUserService,
        ListUserService listUserService,
        PasswordEncoder passwordEncoder,
        UpdateUserService updateUserService
        ) {
        this.createUserService = createUserService;
        this.listUserService = listUserService;
        this.passwordEncoder = passwordEncoder;
        this.updateUserService = updateUserService;
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

    @PutMapping("/{id}")
    public ResponseEntity<Result<ResultUserDTO>> updateUser(@Valid @RequestBody UpdateUserDTO userToUpdateDTO, @PathVariable Integer id) {
        try {
            if(userToUpdateDTO.getPassword() != null && !userToUpdateDTO.getPassword().isEmpty()) {
                userToUpdateDTO.setPassword(this.passwordEncoder.encode(userToUpdateDTO.getPassword()));
            }
            userToUpdateDTO.setId(id);
            User userDomain = UserMapper.updateDTOToUserDomain(userToUpdateDTO);
            
            ResultUserDTO userResult = this.updateUserService.update(userDomain);

            return new ResponseEntity<>(Result.success(userResult, "Usuario actualizado con éxito"), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

