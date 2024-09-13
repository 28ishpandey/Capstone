package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Input Data Transfer Object for creating a new restaurant owner.
 * Contains necessary information such as first name, last name, email, contact number, and password.
 */

/**
 * Input Data Transfer Object for creating a new restaurant owner.
 * Contains necessary information such as first name, last name, email, contact number, and password.
 */
@Data
public class RestaurantOwnerInDTO {

  /**
   * The first name of the restaurant owner.
   */
  @NotBlank(message = "First name is required")
  private String firstName;

  /**
   * The last name of the restaurant owner.
   */
  @NotBlank(message = "Last name is required")
  private String lastName;

  /**
   * The email address of the restaurant owner, must end with gmail.com.
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * The contact number of the restaurant owner, must start with 9, 8, or 7 and be 10 digits.
   */
  @Pattern(regexp = "^[987]\\d{9}$", message = "Contact number should be 10 digits and start with 9, 8, or 7")
  private String contactNumber;

  /**
   * The password of the restaurant owner account.
   */
  @NotBlank(message = "Password is required")
  private String password;
}

