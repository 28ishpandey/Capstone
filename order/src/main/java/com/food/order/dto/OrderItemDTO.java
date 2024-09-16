package com.food.order.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) to represent an individual item in an order.
 */
@Data
public class OrderItemDTO {
  /**
   * The ID of the food item.
   */
  private Long foodItemId;

  /**
   * The name of the food item.
   */
  private String foodItemName;

  /**
   * The quantity of the food item in the order.
   */
  private Integer quantity;

  /**
   * The price of the food item.
   */
  private Double price;
}