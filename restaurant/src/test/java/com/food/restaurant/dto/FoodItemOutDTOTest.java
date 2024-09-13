package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodItemOutDTOTest {

  @Test
  void testId() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getId());

    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  void testCategoryId() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getCategoryId());

    dto.setCategoryId(1L);
    assertEquals(1L, dto.getCategoryId());
  }

  @Test
  void testRestaurantId() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());
  }

  @Test
  void testName() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getName());

    dto.setName("Test Food");
    assertEquals("Test Food", dto.getName());
  }

  @Test
  void testPrice() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertEquals(0.0, dto.getPrice());

    dto.setPrice(9.99);
    assertEquals(9.99, dto.getPrice());
  }

  @Test
  void testAvailability() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertFalse(dto.isAvailability());

    dto.setAvailability(true);
    assertTrue(dto.isAvailability());
  }

  @Test
  void testIsVeg() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertFalse(dto.isVeg());

    dto.setVeg(true);
    assertTrue(dto.isVeg());
  }

  @Test
  void testDescription() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getDescription());

    dto.setDescription("Test description");
    assertEquals("Test description", dto.getDescription());
  }

  @Test
  void testImage() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    assertNull(dto.getImage());

    byte[] image = {1, 2, 3};
    dto.setImage(image);
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    FoodItemOutDTO dto1 = new FoodItemOutDTO();
    dto1.setId(1L);
    dto1.setCategoryId(2L);
    dto1.setRestaurantId(3L);
    dto1.setName("Test Food");

    FoodItemOutDTO dto2 = new FoodItemOutDTO();
    dto2.setId(1L);
    dto2.setCategoryId(2L);
    dto2.setRestaurantId(3L);
    dto2.setName("Test Food");

    FoodItemOutDTO dto3 = new FoodItemOutDTO();
    dto3.setId(4L);
    dto3.setCategoryId(5L);
    dto3.setRestaurantId(6L);
    dto3.setName("Another Food");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    dto.setId(1L);
    dto.setCategoryId(2L);
    dto.setRestaurantId(3L);
    dto.setName("Test Food");
    dto.setPrice(9.99);
    dto.setAvailability(true);
    dto.setVeg(false);
    dto.setDescription("Test description");
    dto.setImage(new byte[]{1, 2, 3});

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("id=1"));
    assertTrue(toStringResult.contains("categoryId=2"));
    assertTrue(toStringResult.contains("restaurantId=3"));
    assertTrue(toStringResult.contains("name=Test Food"));
    assertTrue(toStringResult.contains("price=9.99"));
    assertTrue(toStringResult.contains("availability=true"));
    assertTrue(toStringResult.contains("isVeg=false"));
    assertTrue(toStringResult.contains("description=Test description"));
    assertTrue(toStringResult.contains("image="));
  }
}
