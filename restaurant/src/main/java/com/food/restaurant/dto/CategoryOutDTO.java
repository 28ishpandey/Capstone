package com.food.restaurant.dto;

import lombok.Data;

import java.util.List;

/**
 * Output Data Transfer Object for category information.
 * Contains category details along with associated food items.
 */
@Data
public class CategoryOutDTO {

  /**
   * The unique identifier for the category.
   */
  private Long id;

  /**
   * The unique identifier of the restaurant to which this category belongs.
   */
  private Long restaurantId;

  /**
   * The name of the category.
   */
  private String name;

  /**
   * A list of food items associated with this category.
   */
  private List<FoodItemOutDTO> foodItems;
}
