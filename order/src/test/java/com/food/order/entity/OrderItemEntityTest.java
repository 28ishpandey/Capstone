package com.food.order.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemEntityTest {

  @Test
  void testOrderItemIdField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getOrderItemId());

    Long orderItemId = 1L;
    orderItem.setOrderItemId(orderItemId);
    assertEquals(orderItemId, orderItem.getOrderItemId());

    orderItem.setOrderItemId(null);
    assertNull(orderItem.getOrderItemId());
  }

  @Test
  void testOrderIdField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getOrderId());

    Long orderId = 1L;
    orderItem.setOrderId(orderId);
    assertEquals(orderId, orderItem.getOrderId());

    orderItem.setOrderId(null);
    assertNull(orderItem.getOrderId());
  }

  @Test
  void testFoodItemIdField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getFoodItemId());

    Long foodItemId = 1L;
    orderItem.setFoodItemId(foodItemId);
    assertEquals(foodItemId, orderItem.getFoodItemId());

    orderItem.setFoodItemId(null);
    assertNull(orderItem.getFoodItemId());
  }

  @Test
  void testFoodItemNameField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getFoodItemName());

    String foodItemName = "Pizza";
    orderItem.setFoodItemName(foodItemName);
    assertEquals(foodItemName, orderItem.getFoodItemName());

    orderItem.setFoodItemName(null);
    assertNull(orderItem.getFoodItemName());
    orderItem.setFoodItemName("");
    assertEquals("", orderItem.getFoodItemName());
    String longName = "A".repeat(1000);
    orderItem.setFoodItemName(longName);
    assertEquals(longName, orderItem.getFoodItemName());
  }

  @Test
  void testQuantityField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getQuantity());

    Integer quantity = 5;
    orderItem.setQuantity(quantity);
    assertEquals(quantity, orderItem.getQuantity());

    orderItem.setQuantity(null);
    assertNull(orderItem.getQuantity());
    orderItem.setQuantity(0);
    assertEquals(0, orderItem.getQuantity());
    orderItem.setQuantity(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, orderItem.getQuantity());
  }

  @Test
  void testPriceField() {
    OrderItem orderItem = new OrderItem();
    assertNull(orderItem.getPrice());

    Double price = 9.99;
    orderItem.setPrice(price);
    assertEquals(price, orderItem.getPrice());

    orderItem.setPrice(null);
    assertNull(orderItem.getPrice());
    orderItem.setPrice(0.0);
    assertEquals(0.0, orderItem.getPrice());
    orderItem.setPrice(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, orderItem.getPrice());
  }
}
