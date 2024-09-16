package com.food.order.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderEntityTest {

  @Test
  void testOrderIdField() {
    Order order = new Order();
    assertNull(order.getOrderId());

    Long orderId = 1L;
    order.setOrderId(orderId);
    assertEquals(orderId, order.getOrderId());

    order.setOrderId(null);
    assertNull(order.getOrderId());
  }

  @Test
  void testUserIdField() {
    Order order = new Order();
    assertNull(order.getUserId());

    Long userId = 1L;
    order.setUserId(userId);
    assertEquals(userId, order.getUserId());

    order.setUserId(null);
    assertNull(order.getUserId());
  }

  @Test
  void testRestaurantIdField() {
    Order order = new Order();
    assertNull(order.getRestaurantId());

    Long restaurantId = 1L;
    order.setRestaurantId(restaurantId);
    assertEquals(restaurantId, order.getRestaurantId());

    order.setRestaurantId(null);
    assertNull(order.getRestaurantId());
  }

  @Test
  void testOrderStatusField() {
    Order order = new Order();
    assertNull(order.getOrderStatus());

    OrderStatus status = OrderStatus.PLACED;
    order.setOrderStatus(status);
    assertEquals(status, order.getOrderStatus());

    order.setOrderStatus(null);
    assertNull(order.getOrderStatus());

    // Test all possible enum values
    for (OrderStatus orderStatus : OrderStatus.values()) {
      order.setOrderStatus(orderStatus);
      assertEquals(orderStatus, order.getOrderStatus());
    }
  }

  @Test
  void testPlacedAtField() {
    Order order = new Order();
    assertNull(order.getPlacedAt());

    LocalDateTime placedAt = LocalDateTime.now();
    order.setPlacedAt(placedAt);
    assertEquals(placedAt, order.getPlacedAt());

    order.setPlacedAt(null);
    assertNull(order.getPlacedAt());
  }

  @Test
  void testUpdatedAtField() {
    Order order = new Order();
    assertNull(order.getUpdatedAt());

    LocalDateTime updatedAt = LocalDateTime.now();
    order.setUpdatedAt(updatedAt);
    assertEquals(updatedAt, order.getUpdatedAt());

    order.setUpdatedAt(null);
    assertNull(order.getUpdatedAt());
  }

  @Test
  void testTotalAmountField() {
    Order order = new Order();
    assertNull(order.getTotalAmount());

    Double totalAmount = 100.0;
    order.setTotalAmount(totalAmount);
    assertEquals(totalAmount, order.getTotalAmount());

    order.setTotalAmount(null);
    assertNull(order.getTotalAmount());

    // Test with zero
    order.setTotalAmount(0.0);
    assertEquals(0.0, order.getTotalAmount());

    // Test with negative value (if allowed by your business logic)
    order.setTotalAmount(-50.0);
    assertEquals(-50.0, order.getTotalAmount());

    // Test with very large value
    order.setTotalAmount(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, order.getTotalAmount());
  }
}