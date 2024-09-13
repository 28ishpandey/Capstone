package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantOutDTOTest {

  @Test
  void testId() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getId());

    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  void testEmail() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());
  }

  @Test
  void testContactNumber() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getContactNumber());

    dto.setContactNumber("9123456780");
    assertEquals("9123456780", dto.getContactNumber());
  }

  @Test
  void testName() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getName());

    dto.setName("Test Restaurant");
    assertEquals("Test Restaurant", dto.getName());
  }

  @Test
  void testAddress() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getAddress());

    dto.setAddress("123 Test St");
    assertEquals("123 Test St", dto.getAddress());
  }

  @Test
  void testTimings() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getTimings());

    dto.setTimings("9AM-9PM");
    assertEquals("9AM-9PM", dto.getTimings());
  }

  @Test
  void testIsOpen() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getIsOpen());

    dto.setIsOpen(true);
    assertTrue(dto.getIsOpen());
  }

  @Test
  void testImage() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getImage());

    byte[] image = {1, 2, 3};
    dto.setImage(image);
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    RestaurantOutDTO dto1 = new RestaurantOutDTO();
    dto1.setId(1L);
    dto1.setName("Test Restaurant");

    RestaurantOutDTO dto2 = new RestaurantOutDTO();
    dto2.setId(1L);
    dto2.setName("Test Restaurant");

    RestaurantOutDTO dto3 = new RestaurantOutDTO();
    dto3.setId(2L);
    dto3.setName("Another Restaurant");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    dto.setId(1L);
    dto.setEmail("test@gmail.com");
    dto.setContactNumber("9123456780");
    dto.setName("Test Restaurant");
    dto.setAddress("123 Test St");
    dto.setTimings("9AM-9PM");
    dto.setIsOpen(true);
    dto.setImage(new byte[]{1, 2, 3});

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("id=1"));
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("contactNumber=9123456780"));
    assertTrue(toStringResult.contains("name=Test Restaurant"));
    assertTrue(toStringResult.contains("address=123 Test St"));
    assertTrue(toStringResult.contains("timings=9AM-9PM"));
    assertTrue(toStringResult.contains("isOpen=true"));
    assertTrue(toStringResult.contains("image="));
  }
}
