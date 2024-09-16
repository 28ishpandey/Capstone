package com.food.order.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageDTOTest {

  @Test
  void testMessageField() {
    MessageDTO dto = new MessageDTO();
    assertNull(dto.getMessage());

    String message = "Test message";
    dto.setMessage(message);
    assertEquals(message, dto.getMessage());

    dto.setMessage(null);
    assertNull(dto.getMessage());

    // Test with empty string
    dto.setMessage("");
    assertEquals("", dto.getMessage());

    // Test with very long string
    String longMessage = "A".repeat(1000);
    dto.setMessage(longMessage);
    assertEquals(longMessage, dto.getMessage());
  }
}
