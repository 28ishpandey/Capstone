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

class CategoryInDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testRestaurantId() {
    CategoryInDTO dto = new CategoryInDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());

    dto.setRestaurantId(null);
    Set<ConstraintViolation<CategoryInDTO>> violations = validator.validateProperty(dto, "restaurantId");
    assertEquals(1, violations.size());
    assertEquals("Restaurant ID is required", violations.iterator().next().getMessage());
  }

  @Test
  void testName() {
    CategoryInDTO dto = new CategoryInDTO();
    assertNull(dto.getName());

    dto.setName("Test Category");
    assertEquals("Test Category", dto.getName());

    dto.setName("");
    Set<ConstraintViolation<CategoryInDTO>> violations = validator.validateProperty(dto, "name");
    assertEquals(1, violations.size());
    assertEquals("Category name is required", violations.iterator().next().getMessage());

    dto.setName(null);
    violations = validator.validateProperty(dto, "name");
    assertEquals(1, violations.size());
    assertEquals("Category name is required", violations.iterator().next().getMessage());
  }

  @Test
  void testEqualsAndHashCode() {
    CategoryInDTO dto1 = new CategoryInDTO();
    dto1.setRestaurantId(1L);
    dto1.setName("Test Category");

    CategoryInDTO dto2 = new CategoryInDTO();
    dto2.setRestaurantId(1L);
    dto2.setName("Test Category");

    CategoryInDTO dto3 = new CategoryInDTO();
    dto3.setRestaurantId(2L);
    dto3.setName("Another Category");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    CategoryInDTO dto = new CategoryInDTO();
    dto.setRestaurantId(1L);
    dto.setName("Test Category");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("restaurantId=1"));
    assertTrue(toStringResult.contains("name=Test Category"));
  }
}