package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDetailDTOTest {

  @Test
  void testCategoryId() {
    CategoryDetailsDTO dto = new CategoryDetailsDTO();
    assertNull(dto.getCategoryId());

    dto.setCategoryId(1L);
    assertEquals(1L, dto.getCategoryId());
  }

  @Test
  void testCategoryName() {
    CategoryDetailsDTO dto = new CategoryDetailsDTO();
    assertNull(dto.getCategoryName());

    dto.setCategoryName("Test Category");
    assertEquals("Test Category", dto.getCategoryName());
  }

  @Test
  void testFoodItems() {
    CategoryDetailsDTO dto = new CategoryDetailsDTO();
    assertNull(dto.getFoodItems());

    List<FoodItemDetailsDTO> foodItems = Arrays.asList(new FoodItemDetailsDTO(), new FoodItemDetailsDTO());
    dto.setFoodItems(foodItems);
    assertEquals(2, dto.getFoodItems().size());
    assertSame(foodItems, dto.getFoodItems());
  }

  @Test
  void testEqualsAndHashCode() {
    CategoryDetailsDTO dto1 = new CategoryDetailsDTO();
    dto1.setCategoryId(1L);
    dto1.setCategoryName("Test Category");

    CategoryDetailsDTO dto2 = new CategoryDetailsDTO();
    dto2.setCategoryId(1L);
    dto2.setCategoryName("Test Category");

    CategoryDetailsDTO dto3 = new CategoryDetailsDTO();
    dto3.setCategoryId(2L);
    dto3.setCategoryName("Another Category");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    CategoryDetailsDTO dto = new CategoryDetailsDTO();
    dto.setCategoryId(1L);
    dto.setCategoryName("Test Category");
    dto.setFoodItems(Arrays.asList(new FoodItemDetailsDTO(), new FoodItemDetailsDTO()));

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("categoryId=1"));
    assertTrue(toStringResult.contains("categoryName=Test Category"));
    assertTrue(toStringResult.contains("foodItems="));
  }
}