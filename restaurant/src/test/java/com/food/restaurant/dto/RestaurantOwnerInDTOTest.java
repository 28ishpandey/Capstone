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

class RestaurantOwnerInDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testFirstName() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    assertNull(dto.getFirstName());

    dto.setFirstName("firstname");
    assertEquals("firstname", dto.getFirstName());

    Set<ConstraintViolation<RestaurantOwnerInDTO>> violations = validator.validateProperty(dto, "firstName");
    assertTrue(violations.isEmpty());

    dto.setFirstName("");
    violations = validator.validateProperty(dto, "firstName");
    assertEquals(1, violations.size());
    assertEquals("First name is required", violations.iterator().next().getMessage());
  }

  @Test
  void testLastName() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    assertNull(dto.getLastName());

    dto.setLastName("lastname");
    assertEquals("lastname", dto.getLastName());

    Set<ConstraintViolation<RestaurantOwnerInDTO>> violations = validator.validateProperty(dto, "lastName");
    assertTrue(violations.isEmpty());

    dto.setLastName("");
    violations = validator.validateProperty(dto, "lastName");
    assertEquals(1, violations.size());
    assertEquals("Last name is required", violations.iterator().next().getMessage());
  }

  @Test
  void testEmail() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());

    Set<ConstraintViolation<RestaurantOwnerInDTO>> violations = validator.validateProperty(dto, "email");
    assertTrue(violations.isEmpty());

    dto.setEmail("test@example.com");
    violations = validator.validateProperty(dto, "email");
    assertEquals(1, violations.size());
    assertEquals("Email should end with gmail.com", violations.iterator().next().getMessage());
  }

  @Test
  void testContactNumber() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    assertNull(dto.getContactNumber());

    dto.setContactNumber("9123456780");
    assertEquals("9123456780", dto.getContactNumber());

    Set<ConstraintViolation<RestaurantOwnerInDTO>> violations = validator.validateProperty(dto, "contactNumber");
    assertTrue(violations.isEmpty());

    dto.setContactNumber("1234567890");
    violations = validator.validateProperty(dto, "contactNumber");
    assertEquals(1, violations.size());
    assertEquals("Contact number should be 10 digits and start with 9, 8, or 7", violations.iterator().next().getMessage());
  }

  @Test
  void testPassword() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    assertNull(dto.getPassword());

    dto.setPassword("password123");
    assertEquals("password123", dto.getPassword());

    Set<ConstraintViolation<RestaurantOwnerInDTO>> violations = validator.validateProperty(dto, "password");
    assertTrue(violations.isEmpty());

    dto.setPassword("");
    violations = validator.validateProperty(dto, "password");
    assertEquals(1, violations.size());
    assertEquals("Password is required", violations.iterator().next().getMessage());
  }

  @Test
  void testEqualsAndHashCode() {
    RestaurantOwnerInDTO dto1 = new RestaurantOwnerInDTO();
    dto1.setEmail("test@gmail.com");
    dto1.setFirstName("firstname");
    dto1.setLastName("lastname");

    RestaurantOwnerInDTO dto2 = new RestaurantOwnerInDTO();
    dto2.setEmail("test@gmail.com");
    dto2.setFirstName("firstname");
    dto2.setLastName("lastname");

    RestaurantOwnerInDTO dto3 = new RestaurantOwnerInDTO();
    dto3.setEmail("another@gmail.com");
    dto3.setFirstName("first");
    dto3.setLastName("last");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    dto.setFirstName("firstname");
    dto.setLastName("lastname");
    dto.setEmail("test@gmail.com");
    dto.setContactNumber("9123456780");
    dto.setPassword("password123");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("firstName=firstname"));
    assertTrue(toStringResult.contains("lastName=lastname"));
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("contactNumber=9123456780"));
    assertTrue(toStringResult.contains("password=password123"));
  }
}