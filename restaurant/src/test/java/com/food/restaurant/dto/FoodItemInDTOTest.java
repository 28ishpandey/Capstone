package com.food.restaurant.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodItemInDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testCategoryId() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertNull(dto.getCategoryId());

    dto.setCategoryId(1L);
    assertEquals(1L, dto.getCategoryId());

    dto.setCategoryId(null);
    Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validateProperty(dto, "categoryId");
    assertEquals(1, violations.size());
    assertEquals("Category ID is required", violations.iterator().next().getMessage());
  }

  @Test
  void testRestaurantId() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());

    dto.setRestaurantId(null);
    Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validateProperty(dto, "restaurantId");
    assertEquals(1, violations.size());
    assertEquals("Restaurant ID is required", violations.iterator().next().getMessage());
  }

  @Test
  void testName() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertNull(dto.getName());

    dto.setName("Test Food");
    assertEquals("Test Food", dto.getName());

    dto.setName("");
    Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validateProperty(dto, "name");
    assertEquals(1, violations.size());
    assertEquals("Food item name is required", violations.iterator().next().getMessage());

    dto.setName(null);
    violations = validator.validateProperty(dto, "name");
    assertEquals(1, violations.size());
    assertEquals("Food item name is required", violations.iterator().next().getMessage());
  }

  @Test
  void testPrice() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertEquals(0.0, dto.getPrice());

    dto.setPrice(9.99);
    assertEquals(9.99, dto.getPrice());

    dto.setPrice(-1.0);
    Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validateProperty(dto, "price");
    assertEquals(1, violations.size());
    assertEquals("Price must be greater than zero", violations.iterator().next().getMessage());
  }

  @Test
  void testAvailability() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertFalse(dto.isAvailability());

    dto.setAvailability(true);
    assertTrue(dto.isAvailability());
  }

  @Test
  void testIsVeg() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertFalse(dto.isVeg());

    dto.setVeg(true);
    assertTrue(dto.isVeg());
  }

  @Test
  void testDescription() {
    FoodItemInDTO dto = new FoodItemInDTO();
    assertNull(dto.getDescription());

    dto.setDescription("Test description");
    assertEquals("Test description", dto.getDescription());
  }

  @Test
  void testEqualsAndHashCode() {
    FoodItemInDTO dto1 = new FoodItemInDTO();
    dto1.setCategoryId(1L);
    dto1.setRestaurantId(2L);
    dto1.setName("Test Food");

    FoodItemInDTO dto2 = new FoodItemInDTO();
    dto2.setCategoryId(1L);
    dto2.setRestaurantId(2L);
    dto2.setName("Test Food");

    FoodItemInDTO dto3 = new FoodItemInDTO();
    dto3.setCategoryId(3L);
    dto3.setRestaurantId(4L);
    dto3.setName("Another Food");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    FoodItemInDTO dto = new FoodItemInDTO();
    dto.setCategoryId(1L);
    dto.setRestaurantId(2L);
    dto.setName("Test Food");
    dto.setPrice(9.99);
    dto.setAvailability(true);
    dto.setVeg(false);
    dto.setDescription("Test description");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("categoryId=1"));
    assertTrue(toStringResult.contains("restaurantId=2"));
    assertTrue(toStringResult.contains("name=Test Food"));
    assertTrue(toStringResult.contains("price=9.99"));
    assertTrue(toStringResult.contains("availability=true"));
    assertTrue(toStringResult.contains("isVeg=false"));
    assertTrue(toStringResult.contains("description=Test description"));
  }
}