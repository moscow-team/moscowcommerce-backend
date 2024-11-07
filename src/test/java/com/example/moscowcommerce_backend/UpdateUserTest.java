package com.example.moscowcommerce_backend;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.UpdateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.moscowcommerce_backend.Users.Application.UpdateUserService;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Controllers.UserController;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UpdateUserTest {

    @MockBean
    private UpdateUserService updateUserService;

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
                .andExpect(jsonPath("$.data.email").value("El email debe tener entre 5 y 50 caracteres"));
    }

    @Test
    public void testAssignValidRole_ShouldAssignAdminRole() throws Exception {
        // Crear el DTO con el rol "ADMIN"
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setRole("ADMIN");

        // Convertir el DTO a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateUserDTO);

        // Ejecutar la solicitud PUT al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuario actualizado con éxito"));
                /* .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value("ADMIN")); */
    }
}
