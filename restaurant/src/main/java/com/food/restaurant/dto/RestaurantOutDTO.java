package com.food.restaurant.dto;

import lombok.Data;

/**
 * Output Data Transfer Object for restaurant information.
 * Contains restaurant details such as ID, email, contact number, name, address, timings, and image.
 */
@Data
public class RestaurantOutDTO {

  /**
   * The unique identifier for the restaurant.
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
}
