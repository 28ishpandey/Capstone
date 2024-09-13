package com.food.restaurant.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for representing detailed information about a category.
 * Contains category details along with a list of associated food items.
 */
@Data
public class CategoryDetailsDTO {

  /**
   * The unique identifier for the category.
   */
  private Long categoryId;

  /**
   * The name of the category.
   */
  private String categoryName;

  /**
   * A list of food items associated with this category.
   */
  private List<FoodItemDetailsDTO> foodItems;
}
