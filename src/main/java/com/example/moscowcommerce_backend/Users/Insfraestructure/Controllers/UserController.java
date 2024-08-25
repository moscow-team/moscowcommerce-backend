package com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserService createUserService;

    // Inyección del servicio mediante el constructor
    @Autowired
    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    // Endpoint para crear un usuario
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO user) {
        System.out.print(user.email);
        System.out.print(user.fullName);
        System.out.print(user.password);
        try {
            User userToSave = UserMapper.toDomain(user);
            this.createUserService.create(userToSave);
            return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

