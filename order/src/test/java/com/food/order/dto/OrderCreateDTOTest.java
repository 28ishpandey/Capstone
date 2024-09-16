package com.food.order.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCreateDTOTest {

  @Test
  void testUserIdField() {
    OrderCreateDTO dto = new OrderCreateDTO();
    assertNull(dto.getUserId());

    Long userId = 1L;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    dto.setUserId(null);
    assertNull(dto.getUserId());
  }

  @Test
  void testRestaurantIdField() {
    OrderCreateDTO dto = new OrderCreateDTO();
    assertNull(dto.getRestaurantId());

    Long restaurantId = 1L;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());

    dto.setRestaurantId(null);
    assertNull(dto.getRestaurantId());
  }

  @Test
  void testOrderItemsField() {
    OrderCreateDTO dto = new OrderCreateDTO();
    assertNull(dto.getOrderItems());

    List<OrderItemDTO> orderItems = Arrays.asList(new OrderItemDTO(), new OrderItemDTO());
    dto.setOrderItems(orderItems);
    assertEquals(orderItems, dto.getOrderItems());
    assertEquals(2, dto.getOrderItems().size());

    dto.setOrderItems(null);
    assertNull(dto.getOrderItems());

    // Test with empty list
    dto.setOrderItems(new ArrayList<>());
    assertTrue(dto.getOrderItems().isEmpty());
  }
}