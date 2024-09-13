package com.food.restaurant.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for representing detailed information about a restaurant.
 * Contains restaurant details along with a list of associated categories.
 */
@Data
public class RestaurantDetailsDTO {

  /**
   * The unique identifier for the restaurant.
   */
  private Long restaurantId;

  /**
   * The name of the restaurant.
   */
  private String restaurantName;

  /**
   * The email address of the restaurant, must end with gmail.com.
   */
  private String email;

  /**
   * The contact number of the restaurant.
   */
  private String contactNumber;

  /**
   * The address of the restaurant.
   */
  private String address;

  /**
   * The operational timings of the restaurant.
   */
  private String timings;

  /**
   * The open/closed status of the restaurant.
   */
  private Boolean isOpen;

  /**
   * The image of the restaurant in byte format.
   */
  private byte[] image;

  /**
   * A list of categories associated with the restaurant.
   */
  private List<CategoryDetailsDTO> categories;
}

