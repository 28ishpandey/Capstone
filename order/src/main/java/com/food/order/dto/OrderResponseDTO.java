package com.food.order.dto;

import com.food.order.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Data Transfer Object (DTO) to represent the response of an order operation.
 */
@Data
public class OrderResponseDTO {
  /**
   * The ID of the order.
   */
  private Long orderId;

  /**
   * The ID of the user who placed the order.
   */
  private Long userId;

  /**
   * The ID of the restaurant from which the order was placed.
   */
  private Long restaurantId;

  /**
   * The current status of the order.
   */
  private OrderStatus orderStatus;

  /**
   * The timestamp when the order was placed.
   */
  private LocalDateTime placedAt;

  /**
   * The timestamp when the order was last updated.
   */
  private LocalDateTime updatedAt;

  /**
   * The total amount for the order.
   */
  private double totalAmount;

  /**
   * The list of items included in the order.
   */
  private List<OrderItemDTO> orderItems;
}
