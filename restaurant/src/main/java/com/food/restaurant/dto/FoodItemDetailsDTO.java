package com.food.restaurant.dto;

import lombok.Data;

/**
 * Data Transfer Object for representing detailed information about a food item.
 */
@Data
public class FoodItemDetailsDTO {

  /**
   * The unique identifier for the food item.
   */
  private Long foodItemId;

  /**
   * The name of the food item.
   */
  private String foodItemName;

  /**
   * The price of the food item.
   */
  private double price;

  /**
   * The availability status of the food item.
   */
  private boolean availability;

  /**
   * Indicates if the food item is vegetarian.
   */
  private boolean isVeg;

  /**
   * A description of the food item.
   */
  private String description;

  /**
   * The image of the food item in byte format.
   */
  private byte[] image;
}
