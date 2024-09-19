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

    dto.setFoodItemName("");
    assertEquals("", dto.getFoodItemName());

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
    dto.setQuantity(0);
    assertEquals(0, dto.getQuantity());
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
    dto.setPrice(0.0);
    assertEquals(0.0, dto.getPrice());
    dto.setPrice(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, dto.getPrice());
  }
}