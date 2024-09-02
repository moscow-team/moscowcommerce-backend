package com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Application.ListUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Mappers.UserMapper;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserService createUserService;
    private final ListUserService listUserService;

    // Inyección del servicio mediante el constructor
    @Autowired
    public UserController(
        CreateUserService createUserService,
        ListUserService listUserService
        ) {
        this.createUserService = createUserService;
        this.listUserService = listUserService;
    }

    // Endpoint para crear un usuario
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO user) {
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

    @GetMapping
    public ResponseEntity<Object> listUser() {
        try {
            return new ResponseEntity<>(listUserService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

