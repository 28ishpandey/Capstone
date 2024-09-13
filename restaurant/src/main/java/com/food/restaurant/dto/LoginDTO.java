package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for user login information.
 * Contains user credentials such as email and password.
 */
@Data
public class LoginDTO {

  /**
   * The email address of the user, must end with gmail.com.
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * The password of the user.
   */
  @NotBlank(message = "Password is required")
  private String password;
}
