package com.food.order.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateItemQuantityDTOTest {

  @Test
  void testQuantityField() {
    UpdateItemQuantityDTO dto = new UpdateItemQuantityDTO();
    assertNull(dto.getQuantity());

    Integer quantity = 5;
    dto.setQuantity(quantity);
    assertEquals(quantity, dto.getQuantity());

    dto.setQuantity(null);
    assertNull(dto.getQuantity());
    dto.setQuantity(0);
    assertEquals(0, dto.getQuantity());
    dto.setQuantity(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, dto.getQuantity());
  }
}