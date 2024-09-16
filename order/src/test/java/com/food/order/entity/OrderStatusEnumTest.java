package com.food.order.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusEnumTest {

  @Test
  void testOrderStatusValues() {
    assertEquals(4, OrderStatus.values().length);
    assertEquals(OrderStatus.IN_CART, OrderStatus.valueOf("IN_CART"));
    assertEquals(OrderStatus.PLACED, OrderStatus.valueOf("PLACED"));
    assertEquals(OrderStatus.CANCELLED, OrderStatus.valueOf("CANCELLED"));
    assertEquals(OrderStatus.COMPLETED, OrderStatus.valueOf("COMPLETED"));
  }

  @Test
  void testOrderStatusOrdinal() {
    assertEquals(0, OrderStatus.IN_CART.ordinal());
    assertEquals(1, OrderStatus.PLACED.ordinal());
    assertEquals(2, OrderStatus.CANCELLED.ordinal());
    assertEquals(3, OrderStatus.COMPLETED.ordinal());
  }
}
