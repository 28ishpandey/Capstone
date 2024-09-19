package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantOwnerOutDTOTest {

  @Test
  void testId() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    assertNull(dto.getId());

    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  void testFirstName() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    assertNull(dto.getFirstName());

    dto.setFirstName("firstname");
    assertEquals("firstname", dto.getFirstName());
  }

  @Test
  void testLastName() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    assertNull(dto.getLastName());

    dto.setLastName("lastname");
    assertEquals("lastname", dto.getLastName());
  }

  @Test
  void testEmail() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());
  }

  @Test
  void testContactNumber() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    assertNull(dto.getContactNumber());

    dto.setContactNumber("9123456780");
    assertEquals("9123456780", dto.getContactNumber());
  }

  @Test
  void testEqualsAndHashCode() {
    RestaurantOwnerOutDTO dto1 = new RestaurantOwnerOutDTO();
    dto1.setId(1L);
    dto1.setEmail("test@gmail.com");
    dto1.setFirstName("firstname");
    dto1.setLastName("lastname");

    RestaurantOwnerOutDTO dto2 = new RestaurantOwnerOutDTO();
    dto2.setId(1L);
    dto2.setEmail("test@gmail.com");
    dto2.setFirstName("firstname");
    dto2.setLastName("lastname");

    RestaurantOwnerOutDTO dto3 = new RestaurantOwnerOutDTO();
    dto3.setId(2L);
    dto3.setEmail("another@gmail.com");
    dto3.setFirstName("first");
    dto3.setLastName("last");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantOwnerOutDTO dto = new RestaurantOwnerOutDTO();
    dto.setId(1L);
    dto.setFirstName("first");
    dto.setLastName("last");
    dto.setEmail("test@gmail.com");
    dto.setContactNumber("9123456780");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("id=1"));
    assertTrue(toStringResult.contains("firstName=first"));
    assertTrue(toStringResult.contains("lastName=last"));
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("contactNumber=9123456780"));
  }
}
