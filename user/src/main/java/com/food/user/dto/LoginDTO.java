package com.food.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for user login requests.
 * Contains login credentials for authenticating a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

  /**
   * Email address of the user trying to log in.
   */
  private String email;

  /**
   * Password of the user trying to log in.
   */
  private String password;
}
