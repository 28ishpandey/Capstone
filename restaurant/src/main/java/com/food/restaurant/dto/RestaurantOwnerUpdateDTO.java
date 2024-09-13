package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object for updating information about a restaurant owner.
 * Contains fields for first name, last name, email, and contact number.
 */
@Data
public class RestaurantOwnerUpdateDTO {

  /**
   * The first name of the restaurant owner.
   * Must not be blank.
   */
  @NotBlank(message = "First name is required")
  private String firstName;

  /**
   * The last name of the restaurant owner.
   * Must not be blank.
   */
  @NotBlank(message = "Last name is required")
  private String lastName;

  /**
   * The email address of the restaurant owner.
   * Must end with @gmail.com.
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * The contact number of the restaurant owner.
   * Must be 10 digits long and start with 9, 8, or 7.
   */
  @Pattern(regexp = "^[987]\\d{9}$", message = "Contact number should be 10 digits and start with 9, 8, or 7")
  private String contactNumber;
}
