package com.food.user.usertests;

import com.food.user.dto.UserCreateDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserCreateDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testEmailField() {
    UserCreateDTO dto = new UserCreateDTO();
    assertNull(dto.getEmail());

    String email = "test@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());

    dto.setEmail("invalid-email");
    assertFalse(validator.validate(dto).isEmpty());

    dto.setEmail("");
    assertFalse(validator.validate(dto).isEmpty());
  }

  @Test
  void testContactNumberField() {
    UserCreateDTO dto = new UserCreateDTO();
    assertNull(dto.getContactNumber());

    String contactNumber = "1234567890";
    dto.setContactNumber(contactNumber);
    assertEquals(contactNumber, dto.getContactNumber());

    dto.setContactNumber("12345");
    assertFalse(validator.validate(dto).isEmpty());

    dto.setContactNumber("");
    assertFalse(validator.validate(dto).isEmpty());
  }

  @Test
  void testPasswordField() {
    UserCreateDTO dto = new UserCreateDTO();
    assertNull(dto.getPassword());

    String password = "securePassword123";
    dto.setPassword(password);
    assertEquals(password, dto.getPassword());

    dto.setPassword("");
    assertFalse(validator.validate(dto).isEmpty());
  }

  @Test
  void testFirstNameField() {
    UserCreateDTO dto = new UserCreateDTO();
    assertNull(dto.getFirstName());

    String firstName = "John";
    dto.setFirstName(firstName);
    assertEquals(firstName, dto.getFirstName());

    dto.setFirstName("");
    assertFalse(validator.validate(dto).isEmpty());
  }

  @Test
  void testLastNameField() {
    UserCreateDTO dto = new UserCreateDTO();
    assertNull(dto.getLastName());

    String lastName = "Doe";
    dto.setLastName(lastName);
    assertEquals(lastName, dto.getLastName());

    dto.setLastName("");
    assertFalse(validator.validate(dto).isEmpty());
  }
}