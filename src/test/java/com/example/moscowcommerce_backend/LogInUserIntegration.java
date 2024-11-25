package com.example.moscowcommerce_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.moscowcommerce_backend.Auth.Infrastructure.DTO.LogInDTO;
import com.example.moscowcommerce_backend.Users.Domain.IUserRepository;
import com.example.moscowcommerce_backend.Users.Domain.User;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.UserEntity;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Entities.Enums.Role;
import com.example.moscowcommerce_backend.Users.Insfraestructure.Ports.IUserRepositoryJPA;
import com.example.moscowcommerce_backend.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@SpringBootTest(classes = MoscowcommerceBackendApplicationTests.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class LogInUserIntegration {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
    }

    private static final String VALID_EMAIL = "user@example.com";
    private static final String VALID_PASSWORD = "Password1";
    private static final String VALID_FULLNAME = "Jhon Doe";
    private static final Role ROLE_ADMIN = Role.ADMIN;
    private static final String INVALID_PASSWORD = "Password2";

    private void setUp() {
        User user = new User();
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setFullName(VALID_FULLNAME);
        user.setRole(ROLE_ADMIN);
        user.setArchivedDate(null);
        userRepository.save(user);
    }

    @Test
    void testLogInUserCorrectly() throws Exception {
        this.setUp();
        LogInDTO logInDTO = new LogInDTO(VALID_EMAIL, VALID_PASSWORD);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logInDTO)))
                .andExpect(status().isOk());
    }
}
