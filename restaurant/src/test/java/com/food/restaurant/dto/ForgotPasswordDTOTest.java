package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordDTOTest {

  @Test
  void testEmail() {
    ForgotPasswordDTO dto = new ForgotPasswordDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@example.com");
    assertEquals("test@example.com", dto.getEmail());
  }

  @Test
  void testEqualsAndHashCode() {
    ForgotPasswordDTO dto1 = new ForgotPasswordDTO();
    dto1.setEmail("test@example.com");

    ForgotPasswordDTO dto2 = new ForgotPasswordDTO();
    dto2.setEmail("test@example.com");

    ForgotPasswordDTO dto3 = new ForgotPasswordDTO();
    dto3.setEmail("another@example.com");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    ForgotPasswordDTO dto = new ForgotPasswordDTO();
    dto.setEmail("test@example.com");

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("email=test@example.com"));
  }
}