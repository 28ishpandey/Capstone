package com.food.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Input Data Transfer Object for creating a new category.
 * Contains necessary information such as restaurant ID and category name.
 */
@Data
public class CategoryInDTO {

  /**
   * The unique identifier of the restaurant to which this category belongs.
   */
  @NotNull(message = "Restaurant ID is required")
  private Long restaurantId;

  /**
   * The name of the category to be created.
   */
  @NotBlank(message = "Category name is required")
  private String name;
}
