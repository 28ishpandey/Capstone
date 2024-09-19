package com.food.order.dto;

import com.food.order.entity.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UpdateOrderStatusDTOTest {

  @Test
  void testStatusField() {
    UpdateOrderStatusDTO dto = new UpdateOrderStatusDTO();
    assertNull(dto.getStatus());

    OrderStatus status = OrderStatus.PLACED;
    dto.setStatus(status);
    assertEquals(status, dto.getStatus());

    dto.setStatus(null);
    assertNull(dto.getStatus());
    for (OrderStatus orderStatus : OrderStatus.values()) {
      dto.setStatus(orderStatus);
      assertEquals(orderStatus, dto.getStatus());
    }
  }
}