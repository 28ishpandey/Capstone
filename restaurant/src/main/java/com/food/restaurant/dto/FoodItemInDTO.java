package com.food.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Input Data Transfer Object for creating a new food item.
 * Contains necessary information such as category ID, restaurant ID, and other food item details.
 */
@Data
public class FoodItemInDTO {

  /**
   * The unique identifier of the category to which this food item belongs.
   */
  @NotNull(message = "Category ID is required")
  private Long categoryId;

  /**
   * The unique identifier of the restaurant to which this food item belongs.
   */
  @NotNull(message = "Restaurant ID is required")
  private Long restaurantId;

  /**
   * The name of the food item to be created.
   */
  @NotBlank(message = "Food item name is required")
  private String name;

  /**
   * The price of the food item.
   */
  @Positive(message = "Price must be greater than zero")
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
}
