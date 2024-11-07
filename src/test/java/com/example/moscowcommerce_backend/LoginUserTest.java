package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Auth.Application.AuthService;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Controller.LoginController;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.ResultLogInDTO;
import com.example.moscowcommerce_backend.Auth.Infrastructure.Security.JwtService;
import com.example.moscowcommerce_backend.Shared.Infrastructure.Result;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUserTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        // No es necesario inicializar manualmente ya que @ExtendWith y @InjectMocks lo gestionan
    }

    // Caso de prueba: Inicio de Sesión - Contraseña Incorrecta
    @Test
    void testLoginWithIncorrectPassword() {
        // Arrange: Datos de entrada
        String email = "juan@example.com";
        String incorrectPassword = "IncorrectPassword123";
        LogInDTO logInDTO = new LogInDTO(email, incorrectPassword);

        // Simulamos el comportamiento del AuthService: el servicio devuelve null cuando la contraseña es incorrecta
        when(authService.authenticate(logInDTO)).thenReturn(null);

        // Act: Hacemos la llamada al controlador para autenticar el usuario
        ResponseEntity<Result<ResultLogInDTO>> response = loginController.login(logInDTO);

        // Assert: Verificamos el resultado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isFailure());
        assertEquals("Usuario o contraseña incorrectos", response.getBody().getMessage());
        
        // Verificamos que el AuthService fue llamado correctamente
        verify(authService, times(1)).authenticate(logInDTO);
    }
}
