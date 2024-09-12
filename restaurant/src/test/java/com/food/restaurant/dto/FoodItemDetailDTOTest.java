package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodItemDetailDTOTest {

  @Test
  void testFoodItemId() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertNull(dto.getFoodItemId());

    dto.setFoodItemId(1L);
    assertEquals(1L, dto.getFoodItemId());
  }

  @Test
  void testFoodItemName() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertNull(dto.getFoodItemName());

    dto.setFoodItemName("Test Food");
    assertEquals("Test Food", dto.getFoodItemName());
  }

  @Test
  void testPrice() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertEquals(0.0, dto.getPrice());

    dto.setPrice(9.99);
    assertEquals(9.99, dto.getPrice());
  }

  @Test
  void testAvailability() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertFalse(dto.isAvailability());

    dto.setAvailability(true);
    assertTrue(dto.isAvailability());
  }

  @Test
  void testIsVeg() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertFalse(dto.isVeg());

    dto.setVeg(true);
    assertTrue(dto.isVeg());
  }

  @Test
  void testDescription() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertNull(dto.getDescription());

    dto.setDescription("Test description");
    assertEquals("Test description", dto.getDescription());
  }

  @Test
  void testImage() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    assertNull(dto.getImage());

    byte[] image = {1, 2, 3};
    dto.setImage(image);
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    FoodItemDetailsDTO dto1 = new FoodItemDetailsDTO();
    dto1.setFoodItemId(1L);
    dto1.setFoodItemName("Test Food");

    FoodItemDetailsDTO dto2 = new FoodItemDetailsDTO();
    dto2.setFoodItemId(1L);
    dto2.setFoodItemName("Test Food");

    FoodItemDetailsDTO dto3 = new FoodItemDetailsDTO();
    dto3.setFoodItemId(2L);
    dto3.setFoodItemName("Another Food");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    FoodItemDetailsDTO dto = new FoodItemDetailsDTO();
    dto.setFoodItemId(1L);
    dto.setFoodItemName("Test Food");
    dto.setPrice(9.99);
    dto.setAvailability(true);
    dto.setVeg(false);
    dto.setDescription("Test description");
    dto.setImage(new byte[]{1, 2, 3});

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("foodItemId=1"));
    assertTrue(toStringResult.contains("foodItemName=Test Food"));
    assertTrue(toStringResult.contains("price=9.99"));
    assertTrue(toStringResult.contains("availability=true"));
    assertTrue(toStringResult.contains("isVeg=false"));
    assertTrue(toStringResult.contains("description=Test description"));
    assertTrue(toStringResult.contains("image="));
  }
}