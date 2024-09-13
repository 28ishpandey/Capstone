package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Input Data Transfer Object for creating a new restaurant.
 * Contains necessary information such as email, contact number, password, name, address, timings, and more.
 */
@Data
public class RestaurantInDTO {

  /**
   * The email address of the restaurant, must end with gmail.com.
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * The contact number of the restaurant, must start with 9, 8, or 7 and be 10 digits.
   */
  @Pattern(regexp = "^[987]\\d{9}$", message = "Contact number should be 10 digits and start with 9, 8, or 7")
  private String contactNumber;

  /**
   * The password of the restaurant account.
   */
  @NotBlank(message = "Password is required")
  private String password;

  /**
   * The name of the restaurant.
   */
  @NotBlank(message = "Name is required")
  private String name;

  /**
   * The address of the restaurant.
   */
  @NotBlank(message = "Address is required")
  private String address;

  /**
   * The operational timings of the restaurant.
   */
  @NotBlank(message = "Timings are required")
  private String timings;

  /**
   * The open/closed status of the restaurant.
   */
  private Boolean isOpen;

  /**
   * The unique identifier of the restaurant owner.
   */
  private Long restaurantOwnerId;
}
