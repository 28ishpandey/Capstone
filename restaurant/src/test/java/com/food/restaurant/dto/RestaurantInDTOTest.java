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

class RestaurantInDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testEmail() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "email");
    assertTrue(violations.isEmpty());

    dto.setEmail("test@example.com");
    violations = validator.validateProperty(dto, "email");
    assertEquals(1, violations.size());
    assertEquals("Email should end with gmail.com", violations.iterator().next().getMessage());
  }

  @Test
  void testContactNumber() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getContactNumber());

    dto.setContactNumber("9123456780");
    assertEquals("9123456780", dto.getContactNumber());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "contactNumber");
    assertTrue(violations.isEmpty());

    dto.setContactNumber("1234567890");
    violations = validator.validateProperty(dto, "contactNumber");
    assertEquals(1, violations.size());
    assertEquals("Contact number should be 10 digits and start with 9, 8, or 7", violations.iterator().next().getMessage());
  }

  @Test
  void testPassword() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getPassword());

    dto.setPassword("password123");
    assertEquals("password123", dto.getPassword());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "password");
    assertTrue(violations.isEmpty());

    dto.setPassword("");
    violations = validator.validateProperty(dto, "password");
    assertEquals(1, violations.size());
    assertEquals("Password is required", violations.iterator().next().getMessage());
  }

  @Test
  void testName() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getName());

    dto.setName("Test Restaurant");
    assertEquals("Test Restaurant", dto.getName());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "name");
    assertTrue(violations.isEmpty());

    dto.setName("");
    violations = validator.validateProperty(dto, "name");
    assertEquals(1, violations.size());
    assertEquals("Name is required", violations.iterator().next().getMessage());
  }

  @Test
  void testAddress() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getAddress());

    dto.setAddress("123 Test St");
    assertEquals("123 Test St", dto.getAddress());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "address");
    assertTrue(violations.isEmpty());

    dto.setAddress("");
    violations = validator.validateProperty(dto, "address");
    assertEquals(1, violations.size());
    assertEquals("Address is required", violations.iterator().next().getMessage());
  }

  @Test
  void testTimings() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getTimings());

    dto.setTimings("9AM-9PM");
    assertEquals("9AM-9PM", dto.getTimings());

    Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validateProperty(dto, "timings");
    assertTrue(violations.isEmpty());

    dto.setTimings("");
    violations = validator.validateProperty(dto, "timings");
    assertEquals(1, violations.size());
    assertEquals("Timings are required", violations.iterator().next().getMessage());
  }

  @Test
  void testIsOpen() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getIsOpen());

    dto.setIsOpen(true);
    assertTrue(dto.getIsOpen());
  }

  @Test
  void testRestaurantOwnerId() {
    RestaurantInDTO dto = new RestaurantInDTO();
    assertNull(dto.getRestaurantOwnerId());

    dto.setRestaurantOwnerId(1L);
    assertEquals(1L, dto.getRestaurantOwnerId());
  }

  @Test
  void testEqualsAndHashCode() {
    RestaurantInDTO dto1 = new RestaurantInDTO();
    dto1.setEmail("test@gmail.com");
    dto1.setName("Test Restaurant");

    RestaurantInDTO dto2 = new RestaurantInDTO();
    dto2.setEmail("test@gmail.com");
    dto2.setName("Test Restaurant");

    RestaurantInDTO dto3 = new RestaurantInDTO();
    dto3.setEmail("another@gmail.com");
    dto3.setName("Another Restaurant");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantInDTO dto = new RestaurantInDTO();
    dto.setEmail("test@gmail.com");
    dto.setContactNumber("9123456780");
    dto.setPassword("password123");
    dto.setName("Test Restaurant");
    dto.setAddress("123 Test St");
    dto.setTimings("9AM-9PM");
    dto.setIsOpen(true);
    dto.setRestaurantOwnerId(1L);

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("contactNumber=9123456780"));
    assertTrue(toStringResult.contains("password=password123"));
    assertTrue(toStringResult.contains("name=Test Restaurant"));
    assertTrue(toStringResult.contains("address=123 Test St"));
    assertTrue(toStringResult.contains("timings=9AM-9PM"));
    assertTrue(toStringResult.contains("isOpen=true"));
    assertTrue(toStringResult.contains("restaurantOwnerId=1"));
  }
}