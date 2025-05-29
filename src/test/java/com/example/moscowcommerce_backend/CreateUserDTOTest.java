package com.example.moscowcommerce_backend;

import static org.junit.jupiter.api.Assertions.*;

import com.example.moscowcommerce_backend.Users.Insfraestructure.DTO.CreateUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateUserDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  // 02.01 Registro de cliente nombre valido
  // 02.02 Registro de cliente email valido
  // 02.04 Registro de cliente rol valida
  @Test
  void testValidCreateUserDTO() {
    // DTO válido
    CreateUserDTO dto =
        new CreateUserDTO("Juan Pérez", "juan.perez@example.com", "Password1", "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertTrue(violations.isEmpty(), "No debería haber violaciones para un DTO válido");
  }

  // 01.01 Longitud de full name
  @Test
  void testInvalidFullName() {
    // Nombre completo inválido
    CreateUserDTO dto = new CreateUserDTO(null, "juan.perez@example.com", "Password1", "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(), "Debería haber violaciones para un nombre completo vacío");
    assertEquals("El nombre completo es requerido", violations.iterator().next().getMessage());
  }

  // 01.01 Longitud de full name
  @Test
  void testMaxLengthFullName() {
    CreateUserDTO dto =
        new CreateUserDTO(
            "Juan Sebastián Ramírez González de la Torre López Extra Largo",
            "juan.perez@example.com",
            "Password1",
            "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(), "Debería haber violaciones para un nombre completo vacío");
    assertEquals(
        "El nombre completo debe tener entre 1 y 50 caracteres",
        violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidEmail() {
    // Email inválido
    CreateUserDTO dto = new CreateUserDTO("Juan Pérez", "email-invalido", "Password1", "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(), "Debería haber violaciones para un email inválido");
    assertEquals("El email no es válido", violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidRole() {
    // Rol inválido
    CreateUserDTO dto =
        new CreateUserDTO("Juan Pérez", "juan.perez@example.com", "Password1", "INVALID_ROLE");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(), "Debería haber violaciones para un rol inválido");
    assertEquals(
        "El rol debe ser CUSTOMER o ADMIN (mayúsculas)", violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidPassword() {
    // Contraseña inválida
    CreateUserDTO dto =
        new CreateUserDTO(
            "Juan Pérez",
            "juan.perez@example.com",
            "123456789", // No cumple con el tamaño ni el patrón
            "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(), "Debería haber violaciones para una contraseña inválida");
    assertEquals(
        "La contraseña debe tener al menos una letra mayúscula, una letra minúscula y un número",
        violations.iterator().next().getMessage());
  }

  // 05.01 Prueba de vulnerabilidad de inyección SQL
  @Test
  void testInvalidFullName_SQLInjection() {
    // Nombre completo con inyección SQL
    CreateUserDTO dto =
        new CreateUserDTO(
            "Juan'; DROP TABLE Users; --", "juan.perez@example.com", "Password1", "CUSTOMER");

    Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

    assertFalse(
        violations.isEmpty(),
        "Debería haber violaciones para un nombre completo con inyección SQL");
    assertEquals(
        "El nombre completo solo puede contener letras y espacios",
        violations.iterator().next().getMessage());
  }
}
