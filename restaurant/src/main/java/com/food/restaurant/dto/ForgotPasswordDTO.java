package com.food.restaurant.dto;

import lombok.Data;

/**
 * DTO for handling forgot password requests.
 */
@Data
public class ForgotPasswordDTO {

  /**
   * Email address of the user requesting a password reset.
   */
  private String email;
}
