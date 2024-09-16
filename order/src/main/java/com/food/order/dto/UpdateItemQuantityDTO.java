package com.food.order.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) to update the quantity of an item in an order.
 */
@Data
public class UpdateItemQuantityDTO {
  /**
   * The new quantity of the item.
   */
  private Integer quantity;
}
