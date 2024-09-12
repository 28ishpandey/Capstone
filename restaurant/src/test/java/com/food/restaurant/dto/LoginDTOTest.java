package com.food.restaurant.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testEmail() {
    LoginDTO dto = new LoginDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());

    Set<ConstraintViolation<LoginDTO>> violations = validator.validateProperty(dto, "email");
    assertTrue(violations.isEmpty());

    dto.setEmail("test@example.com");
    violations = validator.validateProperty(dto, "email");
    assertEquals(1, violations.size());
    assertEquals("Email should end with gmail.com", violations.iterator().next().getMessage());
  }

  @Test
  void testPassword() {
    LoginDTO dto = new LoginDTO();
    assertNull(dto.getPassword());

    dto.setPassword("password123");
    assertEquals("password123", dto.getPassword());

    Set<ConstraintViolation<LoginDTO>> violations = validator.validateProperty(dto, "password");
    assertTrue(violations.isEmpty());

    dto.setPassword("");
    violations = validator.validateProperty(dto, "password");
    assertEquals(1, violations.size());
    assertEquals("Password is required", violations.iterator().next().getMessage());

    dto.setPassword(null);
    violations = validator.validateProperty(dto, "password");
    assertEquals(1, violations.size());
    assertEquals("Password is required", violations.iterator().next().getMessage());
  }

  @Test
  void testEqualsAndHashCode() {
    LoginDTO dto1 = new LoginDTO();
    dto1.setEmail("test@gmail.com");
    dto1.setPassword("password123");

    LoginDTO dto2 = new LoginDTO();
    dto2.setEmail("test@gmail.com");
    dto2.setPassword("password123");

    LoginDTO dto3 = new LoginDTO();
    dto3.setEmail("another@gmail.com");
    dto3.setPassword("anotherpassword");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    LoginDTO dto = new LoginDTO();
    dto.setEmail("test@gmail.com");
    dto.setPassword("password123");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("password=password123"));
  }
}