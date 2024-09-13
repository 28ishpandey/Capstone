package com.food.restaurant.dto;

import lombok.Data;

/**
 * Output Data Transfer Object for restaurant owner information.
 * Contains owner details such as ID, first name, last name, email, and contact number.
 */
@Data
public class RestaurantOwnerOutDTO {

  /**
   * The unique identifier for the restaurant owner.
   */
  private Long id;

  /**
   * The first name of the restaurant owner.
   */
  private String firstName;

  /**
   * The last name of the restaurant owner.
   */
  private String lastName;

  /**
   * The email address of the restaurant owner.
   */
  private String email;

  /**
   * The contact number of the restaurant owner.
   */
  private String contactNumber;
}
