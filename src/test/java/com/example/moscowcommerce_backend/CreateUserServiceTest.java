package com.example.moscowcommerce_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moscowcommerce_backend.Users.Application.CreateUserService;
import com.example.moscowcommerce_backend.Users.Domain.Exceptions.UserAlreadyExistsException;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResultUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceTest {

  private static final String VALID_EMAIL = "user@example.com";
  private static final String VALID_PASSWORD = "Password1";
  private static final Role VALID_ROLE = Role.CUSTOMER;

  @Mock private IUserRepository repository;

  @InjectMocks private CreateUserService createUserService;

  @Test
  void shouldThrowExceptionWhenUserWithEmailAlreadyExists() {
    // Arrange
    User existingUser = createTestUser();
    when(repository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(existingUser));

    // Act & Assert
    UserAlreadyExistsException exception =
        assertThrows(
            UserAlreadyExistsException.class, () -> createUserService.create(existingUser));

    assertEquals("El usuario ya existe.", exception.getMessage());
    verify(repository).findByEmail(VALID_EMAIL);
    verify(repository, never()).save(any(User.class));
  }

  @Test
  void shouldCreateUserWhenEmailDoesNotExist() {
    // Arrange
    User newUser = createTestUser();
    when(repository.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
    when(repository.save(newUser)).thenReturn(newUser);

    // Act
    ResultUserDTO result = createUserService.create(newUser);

    // Assert
    assertNotNull(result);
    assertEquals(VALID_EMAIL, result.getEmail());
    assertEquals(VALID_ROLE, result.getRole());
    verify(repository).findByEmail(VALID_EMAIL);
    verify(repository).save(newUser);
  }

  private User createTestUser() {
    User user = new User();
    user.setEmail(VALID_EMAIL);
    user.setPassword(VALID_PASSWORD);
    user.setRole(VALID_ROLE);
    return user;
  }
}
