package com.food.order.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantOutDTOTest {

  @Test
  void testIdField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getId());

    Long id = 1L;
    dto.setId(id);
    assertEquals(id, dto.getId());

    dto.setId(null);
    assertNull(dto.getId());
  }

  @Test
  void testEmailField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getEmail());

    String email = "test@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());

    dto.setEmail(null);
    assertNull(dto.getEmail());

    // Test with empty string
    dto.setEmail("");
    assertEquals("", dto.getEmail());

    // Test with very long email
    String longEmail = "a".repeat(100) + "@example.com";
    dto.setEmail(longEmail);
    assertEquals(longEmail, dto.getEmail());
  }

  @Test
  void testContactNumberField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getContactNumber());

    String contactNumber = "1234567890";
    dto.setContactNumber(contactNumber);
    assertEquals(contactNumber, dto.getContactNumber());

    dto.setContactNumber(null);
    assertNull(dto.getContactNumber());
    dto.setContactNumber("");
    assertEquals("", dto.getContactNumber());
    String longNumber = "1".repeat(20);
    dto.setContactNumber(longNumber);
    assertEquals(longNumber, dto.getContactNumber());
  }

  @Test
  void testNameField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getName());

    String name = "Test Restaurant";
    dto.setName(name);
    assertEquals(name, dto.getName());

    dto.setName(null);
    assertNull(dto.getName());
    dto.setName("");
    assertEquals("", dto.getName());
    String longName = "A".repeat(1000);
    dto.setName(longName);
    assertEquals(longName, dto.getName());
  }

  @Test
  void testAddressField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getAddress());

    String address = "123 Test St, Test City, 12345";
    dto.setAddress(address);
    assertEquals(address, dto.getAddress());

    dto.setAddress(null);
    assertNull(dto.getAddress());
    dto.setAddress("");
    assertEquals("", dto.getAddress());
    String longAddress = "A".repeat(1000);
    dto.setAddress(longAddress);
    assertEquals(longAddress, dto.getAddress());
  }

  @Test
  void testTimingsField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getTimings());

    String timings = "9:00 AM - 10:00 PM";
    dto.setTimings(timings);
    assertEquals(timings, dto.getTimings());

    dto.setTimings(null);
    assertNull(dto.getTimings());

    dto.setTimings("");
    assertEquals("", dto.getTimings());
    String longTimings = "A".repeat(1000);
    dto.setTimings(longTimings);
    assertEquals(longTimings, dto.getTimings());
  }

  @Test
  void testIsOpenField() {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    assertNull(dto.getIsOpen());

    dto.setIsOpen(true);
    assertTrue(dto.getIsOpen());

    dto.setIsOpen(false);
    assertFalse(dto.getIsOpen());

    dto.setIsOpen(null);
    assertNull(dto.getIsOpen());
  }
}