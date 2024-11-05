package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers.UserController;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterUserTest {

    @Mock
    private CreateUserService createUserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Caso de prueba 1: Registro de Cliente - Longitud del Nombre
    @Test
    void testCreateUserWithInvalidFullNameLength() {
        // Nombre vacío
        CreateUserDTO dtoEmptyName = new CreateUserDTO("", "test@example.com", "Password1", "CUSTOMER");
        Set<ConstraintViolation<CreateUserDTO>> violationsEmpty = validator.validate(dtoEmptyName);
        assertFalse(violationsEmpty.isEmpty());
        assertEquals("El nombre completo es requerido", violationsEmpty.iterator().next().getMessage());

        // Nombre de 51 caracteres
        CreateUserDTO dtoLongName = new CreateUserDTO("A".repeat(51), "test@example.com", "Password1", "CUSTOMER");
        Set<ConstraintViolation<CreateUserDTO>> violationsLong = validator.validate(dtoLongName);
        assertFalse(violationsLong.isEmpty());
        assertEquals("El nombre completo debe tener entre 1 y 50 caracteres", violationsLong.iterator().next().getMessage());
    }

    // Caso de prueba 2: Registro de Cliente - Longitud de Contraseña
    @Test
    void testCreateUserWithInvalidPassword() {
        // Contraseña de 7 caracteres
        CreateUserDTO dtoShortPassword = new CreateUserDTO("John Doe", "test@example.com", "Short1", "CUSTOMER");
        Set<ConstraintViolation<CreateUserDTO>> violationsShort = validator.validate(dtoShortPassword);
        assertFalse(violationsShort.isEmpty());
        assertEquals("La contraseña debe tener entre 8 y 50 caracteres", violationsShort.iterator().next().getMessage());

        // Contraseña de 8 caracteres que no cumple los requisitos
        CreateUserDTO dtoWeakPassword = new CreateUserDTO("John Doe", "test@example.com", "password", "CUSTOMER");
        Set<ConstraintViolation<CreateUserDTO>> violationsWeak = validator.validate(dtoWeakPassword);
        assertFalse(violationsWeak.isEmpty());
        assertEquals("La contraseña debe tener al menos una letra mayúscula, una letra minúscula y un número", violationsWeak.iterator().next().getMessage());
    }

    // Caso exitoso de creación de usuario
    @Test
    void testCreateUserSuccess() throws UserAlreadyExistsException {
        CreateUserDTO validDto = new CreateUserDTO("John Doe", "test@example.com", "Password1", "CUSTOMER");
        User user = UserMapper.toDomainFromDTO(validDto);
        ResultUserDTO resultUserDTO = new ResultUserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getRole());

        when(createUserService.create(any(User.class))).thenReturn(resultUserDTO);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        ResponseEntity<Result<ResultUserDTO>> response = userController.createUser(validDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario creado con éxito", response.getBody().getMessage());
        verify(createUserService, times(1)).create(any(User.class));
    }

    // Caso de usuario ya existente
    @Test
    void testCreateUserAlreadyExists() throws UserAlreadyExistsException {
        CreateUserDTO existingUserDto = new CreateUserDTO("Jane Doe", "existing@example.com", "Password1", "CUSTOMER");

        doThrow(new UserAlreadyExistsException("El usuario ya existe")).when(createUserService).create(any(User.class));

        ResponseEntity<Result<ResultUserDTO>> response = userController.createUser(existingUserDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El usuario ya existe", response.getBody().getMessage());
        verify(createUserService, times(1)).create(any(User.class));
    }
}