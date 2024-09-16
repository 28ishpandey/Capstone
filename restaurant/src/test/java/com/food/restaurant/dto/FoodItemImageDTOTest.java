package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;

class FoodItemImageDTOTest {

  @Test
  void testFoodItemId() {
    FoodItemImageDTO dto = new FoodItemImageDTO();
    assertNull(dto.getFoodItemId());

    dto.setFoodItemId(1L);
    assertEquals(1L, dto.getFoodItemId());
  }

  @Test
  void testImage() {
    FoodItemImageDTO dto = new FoodItemImageDTO();
    assertNull(dto.getImage());

    MockMultipartFile file = new MockMultipartFile("test", "test.jpg", "image/jpeg", "test".getBytes());
    dto.setImage(file);
    assertEquals(file, dto.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    MockMultipartFile file1 = new MockMultipartFile("test1", "test1.jpg", "image/jpeg", "test1".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("test2", "test2.jpg", "image/jpeg", "test2".getBytes());

    FoodItemImageDTO dto1 = new FoodItemImageDTO();
    dto1.setFoodItemId(1L);
    dto1.setImage(file1);

    FoodItemImageDTO dto2 = new FoodItemImageDTO();
    dto2.setFoodItemId(1L);
    dto2.setImage(file1);

    FoodItemImageDTO dto3 = new FoodItemImageDTO();
    dto3.setFoodItemId(2L);
    dto3.setImage(file2);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    FoodItemImageDTO dto = new FoodItemImageDTO();
    dto.setFoodItemId(1L);
    MockMultipartFile file = new MockMultipartFile("test", "test.jpg", "image/jpeg", "test".getBytes());
    dto.setImage(file);

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("foodItemId=1"));
    assertTrue(toStringResult.contains("image="));
  }
}