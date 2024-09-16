package com.food.order.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemDTOTest {

  @Test
  void testFoodItemIdField() {
    OrderItemDTO dto = new OrderItemDTO();
    assertNull(dto.getFoodItemId());

    Long foodItemId = 1L;
    dto.setFoodItemId(foodItemId);
    assertEquals(foodItemId, dto.getFoodItemId());

    dto.setFoodItemId(null);
    assertNull(dto.getFoodItemId());
  }

  @Test
  void testFoodItemNameField() {
    OrderItemDTO dto = new OrderItemDTO();
    assertNull(dto.getFoodItemName());

    String foodItemName = "Pizza";
    dto.setFoodItemName(foodItemName);
    assertEquals(foodItemName, dto.getFoodItemName());

    dto.setFoodItemName(null);
    assertNull(dto.getFoodItemName());

    // Test with empty string
    dto.setFoodItemName("");
    assertEquals("", dto.getFoodItemName());

    // Test with very long string
    String longName = "A".repeat(1000);
    dto.setFoodItemName(longName);
    assertEquals(longName, dto.getFoodItemName());
  }

  @Test
  void testQuantityField() {
    OrderItemDTO dto = new OrderItemDTO();
    assertNull(dto.getQuantity());

    Integer quantity = 5;
    dto.setQuantity(quantity);
    assertEquals(quantity, dto.getQuantity());

    dto.setQuantity(null);
    assertNull(dto.getQuantity());

    // Test with zero
    dto.setQuantity(0);
    assertEquals(0, dto.getQuantity());

    // Test with negative value (if allowed by your business logic)
    dto.setQuantity(-1);
    assertEquals(-1, dto.getQuantity());

    // Test with very large value
    dto.setQuantity(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, dto.getQuantity());
  }

  @Test
  void testPriceField() {
    OrderItemDTO dto = new OrderItemDTO();
    assertNull(dto.getPrice());

    Double price = 9.99;
    dto.setPrice(price);
    assertEquals(price, dto.getPrice());

    dto.setPrice(null);
    assertNull(dto.getPrice());

    // Test with zero
    dto.setPrice(0.0);
    assertEquals(0.0, dto.getPrice());

    // Test with negative value (if allowed by your business logic)
    dto.setPrice(-5.0);
    assertEquals(-5.0, dto.getPrice());

    // Test with very large value
    dto.setPrice(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, dto.getPrice());
  }
}