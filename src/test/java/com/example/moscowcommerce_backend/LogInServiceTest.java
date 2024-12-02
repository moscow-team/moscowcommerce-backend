package com.example.moscowcommerce_backend;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.moscowcommerce_backend.Auth.Application.AuthService;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Users.Application.ListUserService;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;

@ExtendWith(MockitoExtension.class)
public class LogInServiceTest {
    private static final String VALID_EMAIL = "user@example.com";
    private static final String VALID_PASSWORD = "Password1";
    private static final String INVALID_PASSWORD = "Password2";

    private final LogInDTO dtoInvalidPass = new LogInDTO(VALID_EMAIL, INVALID_PASSWORD);

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ListUserService listUserService;

    @InjectMocks
    private AuthService authService;

    // 02.03 Inicio de sesión con email válido y contraseña válida
    @Test
    void testInvalidPassword() {
        User user = getUser();
        when(listUserService.findByEmail(VALID_EMAIL)).thenReturn(user);
        var userLogged = authService.authenticate(dtoInvalidPass);
        assertNull(userLogged, "El usuario debería ser nulo si la contraseña es incorrecta");
        verify(listUserService).findByEmail(VALID_EMAIL);
    }

    @Test
    void testUserNotFound() {
        when(listUserService.findByEmail(VALID_EMAIL)).thenThrow(new RuntimeException("User not found"));
        var userLogged = authService.authenticate(dtoInvalidPass);
        assertNull(userLogged, "El usuario debería ser nulo si no se encuentra");
        verify(listUserService).findByEmail(VALID_EMAIL);
    }

    private User getUser() {
        User user = new User();
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setArchivedDate(null);
        return user;
    }
}
