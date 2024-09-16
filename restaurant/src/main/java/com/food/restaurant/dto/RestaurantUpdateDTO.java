package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object for updating information about a restaurant.
 * Contains fields for email, contact number, name, address, timings, and operational status.
 */
@Data
public class RestaurantUpdateDTO {

  /**
   * The email address of the restaurant.
   * Must end with @gmail.com.
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * The contact number of the restaurant.
   * Must be 10 digits long and start with 9, 8, or 7.
   */
  @Pattern(regexp = "^[987]\\d{9}$", message = "Contact number should be 10 digits and start with 9, 8, or 7")
  private String contactNumber;

  /**
   * The name of the restaurant.
   * Must not be blank.
   */
  @NotBlank(message = "Name is required")
  private String name;

  /**
   * The address of the restaurant.
   * Must not be blank.
   */
  @NotBlank(message = "Address is required")
  private String address;

  /**
   * The operational timings of the restaurant.
   * Must not be blank.
   */
  @NotBlank(message = "Timings are required")
  private String timings;

  /**
   * The open/closed status of the restaurant.
   * Can be null if not specified.
   */
  private Boolean isOpen;
}
