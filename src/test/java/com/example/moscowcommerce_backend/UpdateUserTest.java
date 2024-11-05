package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.UpdateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.moscowcommerce_backend.Users.Application.UpdateUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers.UserController;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UpdateUserTest {

    @Mock
    private UpdateUserService updateUserService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateUser_EmailTooLong_ShouldReturnError() throws Exception {
        // Crear un email con más de 254 caracteres
        String longEmail = "a".repeat(255) + "@example.com";

        // Crear el DTO con el email largo
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setEmail(longEmail);
        updateUserDTO.setFullName("John Doe");
        updateUserDTO.setPassword("Password123");
        updateUserDTO.setRole("CUSTOMER");

        // Convertir el DTO a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateUserDTO);

        // Ejecutar la solicitud PUT al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El correo electrónico no puede exceder 254 caracteres"));
    }
}
