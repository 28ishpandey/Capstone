package com.food.order.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) to represent restaurant details.
 */
@Data
public class RestaurantOutDTO {

  /**
   * The ID of the restaurant.
   */
  private Long id;

  /**
   * The email address of the restaurant.
   */
  private String email;

  /**
   * The contact number of the restaurant.
   */
  private String contactNumber;

  /**
   * The name of the restaurant.
   */
  private String name;

  /**
   * The address of the restaurant.
   */
  private String address;

  /**
   * The operating hours or timings of the restaurant.
   */
  private String timings;

  /**
   * The open/closed status of the restaurant.
   */
  private Boolean isOpen;
}
