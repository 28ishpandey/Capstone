package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryOutDTOTest {

  @Test
  void testId() {
    CategoryOutDTO dto = new CategoryOutDTO();
    assertNull(dto.getId());

    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  void testRestaurantId() {
    CategoryOutDTO dto = new CategoryOutDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());
  }

  @Test
  void testName() {
    CategoryOutDTO dto = new CategoryOutDTO();
    assertNull(dto.getName());

    dto.setName("Test Category");
    assertEquals("Test Category", dto.getName());
  }

  @Test
  void testFoodItems() {
    CategoryOutDTO dto = new CategoryOutDTO();
    assertNull(dto.getFoodItems());

    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    dto.setFoodItems(foodItems);
    assertEquals(2, dto.getFoodItems().size());
    assertSame(foodItems, dto.getFoodItems());
  }

  @Test
  void testEqualsAndHashCode() {
    CategoryOutDTO dto1 = new CategoryOutDTO();
    dto1.setId(1L);
    dto1.setRestaurantId(2L);
    dto1.setName("Test Category");

    CategoryOutDTO dto2 = new CategoryOutDTO();
    dto2.setId(1L);
    dto2.setRestaurantId(2L);
    dto2.setName("Test Category");

    CategoryOutDTO dto3 = new CategoryOutDTO();
    dto3.setId(3L);
    dto3.setRestaurantId(4L);
    dto3.setName("Another Category");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    CategoryOutDTO dto = new CategoryOutDTO();
    dto.setId(1L);
    dto.setRestaurantId(2L);
    dto.setName("Test Category");
    dto.setFoodItems(Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO()));

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("id=1"));
    assertTrue(toStringResult.contains("restaurantId=2"));
    assertTrue(toStringResult.contains("name=Test Category"));
    assertTrue(toStringResult.contains("foodItems="));
  }
}