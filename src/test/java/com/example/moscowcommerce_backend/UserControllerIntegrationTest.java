package com.example.moscowcommerce_backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.ResetPasswordDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.UpdateUserDTO;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;
import com.example.moscowcommerce_backend.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MoscowcommerceBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class UserControllerIntegrationTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private IUserRepository userRepository;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private PasswordEncoder passwordEncoder;

  private Integer createdUserId;

  @BeforeEach
  void setup() {
    User user = new User();
    user.setFullName("Juan Perez");
    user.setEmail("test@example.com");
    user.setPassword(passwordEncoder.encode("Password123"));
    user.setRole(Role.ADMIN);

    User savedUser = userRepository.save(user);

    createdUserId = savedUser.getId();
    System.out.println("User created with ID: " + createdUserId);
  }

  // 1. Test para listar usuarios
  @Test
  void listUsersSuccessfully() throws Exception {
    mockMvc
        .perform(get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").isArray());
  }

  // 2. Test para crear un usuario
  @Test
  void createUserSuccessfully() throws Exception {
    CreateUserDTO userDTO = new CreateUserDTO();
    userDTO.setFullName("New User");
    userDTO.setEmail("newuser@example.com");
    userDTO.setPassword("Password123");
    userDTO.setRole(String.valueOf(Role.ADMIN));

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.fullName").value(userDTO.getFullName()))
        .andExpect(jsonPath("$.data.email").value(userDTO.getEmail()))
        .andExpect(jsonPath("$.data.role").value(userDTO.getRole()));
  }

  @Test
  void createUserWithExistingEmail() throws Exception {
    CreateUserDTO userDTO = new CreateUserDTO();
    userDTO.setFullName("anotherUser");
    userDTO.setEmail("test@example.com"); // Email ya existente
    userDTO.setPassword("anotherPassword123");
    userDTO.setRole(String.valueOf(Role.ADMIN));

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("El usuario ya existe."));
  }

  // 3. Test para actualizar un usuario
  @Test
  void updateUserSuccessfully() throws Exception {
    UpdateUserDTO updateDTO = new UpdateUserDTO();
    updateDTO.setFullName("updatedUser");
    updateDTO.setEmail("updated@example.com");

    mockMvc
        .perform(
            put("/users/" + createdUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.fullName").value(updateDTO.getFullName()))
        .andExpect(jsonPath("$.data.email").value(updateDTO.getEmail()));
  }

  @Test
  void updateUserNonExistent() throws Exception {
    UpdateUserDTO updateDTO = new UpdateUserDTO();
    updateDTO.setFullName("updatedUser");
    updateDTO.setEmail("updated@example.com");

    mockMvc
        .perform(
            put("/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Usuario no encontrado con el id: 999"));
  }

  // 4. Test para resetear contraseña
  @Test
  void resetPasswordSuccessfully() throws Exception {
    ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
    resetPasswordDTO.setEmail("test@example.com");

    mockMvc
        .perform(
            post("/users/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Contraseña modificada con éxito"))
        .andExpect(jsonPath("$.data.email").value(resetPasswordDTO.getEmail()));
  }

  // 5. Test para eliminar un usuario
  @Test
  void deleteUserSuccessfully() throws Exception {
    mockMvc
        .perform(delete("/users/" + createdUserId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Usuario eliminado con éxito"));
  }

  @Test
  void deleteNonExistentUser() throws Exception {
    mockMvc
        .perform(delete("/users/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Usuario no encontrado con el id: 999"));
  }

  // 6. Test para desarchivar un usuario
  @Test
  void unarchivedUserSuccessfully() throws Exception {
    mockMvc
        .perform(delete("/users/" + createdUserId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc
        .perform(patch("/users/" + createdUserId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Usuario desarchivado con éxito"));
  }

  @Test
  void unarchivedNonExistentUser() throws Exception {
    mockMvc
        .perform(patch("/users/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Usuario no encontrado con el id: 999"));
  }
}
