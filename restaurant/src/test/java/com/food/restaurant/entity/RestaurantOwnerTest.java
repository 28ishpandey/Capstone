package com.food.restaurant.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantOwnerTest {

  private Validator validator;
  private RestaurantOwner owner;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    owner = new RestaurantOwner();
  }

  @Test
  void testIdField() {
    assertNull(owner.getId());
    owner.setId(1L);
    assertEquals(1L, owner.getId());
  }

  @Test
  void testFirstNameField() {
    assertNull(owner.getFirstName());
    owner.setFirstName("John");
    assertEquals("John", owner.getFirstName());
  }

  @Test
  void testLastNameField() {
    assertNull(owner.getLastName());
    owner.setLastName("Doe");
    assertEquals("Doe", owner.getLastName());
  }

  @Test
  void testEmailField() {
    assertNull(owner.getEmail());
    owner.setEmail("john.doe@gmail.com");
    assertEquals("john.doe@gmail.com", owner.getEmail());

    Set<ConstraintViolation<RestaurantOwner>> violations = validator.validateProperty(owner, "email");
    assertTrue(violations.isEmpty());

    owner.setEmail("john.doe@example.com");
    violations = validator.validateProperty(owner, "email");
    assertFalse(violations.isEmpty());
    assertEquals("Email should end with gmail.com", violations.iterator().next().getMessage());
  }

  @Test
  void testContactNumberField() {
    assertNull(owner.getContactNumber());
    owner.setContactNumber("1234567890");
    assertEquals("1234567890", owner.getContactNumber());

    Set<ConstraintViolation<RestaurantOwner>> violations = validator.validateProperty(owner, "contactNumber");
    assertTrue(violations.isEmpty());
  }

  @Test
  void testPasswordField() {
    assertNull(owner.getPassword());
    owner.setPassword("password123");
    assertEquals("password123", owner.getPassword());

    Set<ConstraintViolation<RestaurantOwner>> violations = validator.validateProperty(owner, "password");
    assertTrue(violations.isEmpty());
  }
}