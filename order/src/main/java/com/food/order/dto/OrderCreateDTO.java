package com.food.order.dto;

import lombok.Data;

import java.util.List;


/**
 * Data Transfer Object (DTO) for creating a new order.
 */
@Data
public class OrderCreateDTO {
  /**
   * The ID of the user placing the order.
   */
  private Long userId;

  /**
   * The ID of the restaurant from which the order is placed.
   */
  private Long restaurantId;

  /**
   * The list of items being ordered.
   */
  private List<OrderItemDTO> orderItems;
}
