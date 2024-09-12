package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantImageDTOTest {

  @Test
  void testRestaurantId() {
    RestaurantImageDTO dto = new RestaurantImageDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());
  }

  @Test
  void testImage() {
    RestaurantImageDTO dto = new RestaurantImageDTO();
    assertNull(dto.getImage());

    MockMultipartFile file = new MockMultipartFile("test", "test.jpg", "image/jpeg", "test".getBytes());
    dto.setImage(file);
    assertEquals(file, dto.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    MockMultipartFile file1 = new MockMultipartFile("test1", "test1.jpg", "image/jpeg", "test1".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("test2", "test2.jpg", "image/jpeg", "test2".getBytes());

    RestaurantImageDTO dto1 = new RestaurantImageDTO();
    dto1.setRestaurantId(1L);
    dto1.setImage(file1);

    RestaurantImageDTO dto2 = new RestaurantImageDTO();
    dto2.setRestaurantId(1L);
    dto2.setImage(file1);

    RestaurantImageDTO dto3 = new RestaurantImageDTO();
    dto3.setRestaurantId(2L);
    dto3.setImage(file2);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantImageDTO dto = new RestaurantImageDTO();
    dto.setRestaurantId(1L);
    MockMultipartFile file = new MockMultipartFile("test", "test.jpg", "image/jpeg", "test".getBytes());
    dto.setImage(file);

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("restaurantId=1"));
    assertTrue(toStringResult.contains("image="));
  }
}