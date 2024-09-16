package com.food.order.dto;

import com.food.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * Data Transfer Object (DTO) to update the status of an order.
 */
@Data
public class UpdateOrderStatusDTO {
  /**
   * The new status of the order.
   * This field is mandatory.
   */
  @NotNull(message = "Order status is required")
  private OrderStatus status;
}

