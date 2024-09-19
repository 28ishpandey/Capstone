package com.food.order.dto;

import com.food.order.entity.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderResponseDTOTest {

  @Test
  void testOrderIdField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getOrderId());

    Long orderId = 1L;
    dto.setOrderId(orderId);
    assertEquals(orderId, dto.getOrderId());

    dto.setOrderId(null);
    assertNull(dto.getOrderId());
  }

  @Test
  void testUserIdField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getUserId());

    Long userId = 1L;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    dto.setUserId(null);
    assertNull(dto.getUserId());
  }

  @Test
  void testRestaurantIdField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getRestaurantId());

    Long restaurantId = 1L;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());

    dto.setRestaurantId(null);
    assertNull(dto.getRestaurantId());
  }

  @Test
  void testOrderStatusField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getOrderStatus());

    OrderStatus status = OrderStatus.PLACED;
    dto.setOrderStatus(status);
    assertEquals(status, dto.getOrderStatus());

    dto.setOrderStatus(null);
    assertNull(dto.getOrderStatus());
    for (OrderStatus orderStatus : OrderStatus.values()) {
      dto.setOrderStatus(orderStatus);
      assertEquals(orderStatus, dto.getOrderStatus());
    }
  }

  @Test
  void testPlacedAtField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getPlacedAt());

    LocalDateTime placedAt = LocalDateTime.now();
    dto.setPlacedAt(placedAt);
    assertEquals(placedAt, dto.getPlacedAt());

    dto.setPlacedAt(null);
    assertNull(dto.getPlacedAt());
  }

  @Test
  void testUpdatedAtField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertNull(dto.getUpdatedAt());

    LocalDateTime updatedAt = LocalDateTime.now();
    dto.setUpdatedAt(updatedAt);
    assertEquals(updatedAt, dto.getUpdatedAt());

    dto.setUpdatedAt(null);
    assertNull(dto.getUpdatedAt());
  }

  @Test
  void testTotalAmountField() {
    OrderResponseDTO dto = new OrderResponseDTO();
    assertEquals(0.0, dto.getTotalAmount()); // Assuming default is 0.0

    double totalAmount = 100.0;
    dto.setTotalAmount(totalAmount);
    assertEquals(totalAmount, dto.getTotalAmount());
    dto.setTotalAmount(0.0);
    assertEquals(0.0, dto.getTotalAmount());
    dto.setTotalAmount(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, dto.getTotalAmount());
  }
}