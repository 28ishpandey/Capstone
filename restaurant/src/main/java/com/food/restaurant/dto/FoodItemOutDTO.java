package com.food.restaurant.dto;

import lombok.Data;

/**
 * Output Data Transfer Object for food item information.
 * Contains food item details such as ID, name, price, availability, and other relevant information.
 */
@Data
public class FoodItemOutDTO {

  /**
   * The unique identifier for the food item.
   */
  private Long id;

  /**
   * The unique identifier of the category to which this food item belongs.
   */
  private Long categoryId;

  /**
   * The unique identifier of the restaurant to which this food item belongs.
   */
  private Long restaurantId;

  /**
   * The name of the food item.
   */
  private String name;

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
