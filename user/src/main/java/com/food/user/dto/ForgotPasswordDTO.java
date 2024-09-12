package com.food.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for handling forgot password requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {

  /**
   * Email address of the user requesting a password reset.
   */
  private String email;
}
