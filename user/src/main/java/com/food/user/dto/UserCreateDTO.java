package com.food.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * Contains information required to register a new user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

  /**
   * Email address of the user. Must be a valid email and end with '.com'.
   */
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  private String email;

  /**
   * Contact number of the user. Must be exactly 10 digits.
   */
  @NotBlank(message = "contact number cannot be blank")
  @Pattern(regexp = "^[987]\\d{9}$", message = "Contact number should be 10 digits and start with 9, 8, or 7")
  private String contactNumber;

  /**
   * Password for the user's account.
   */
  @NotBlank(message = "password cannot be blank")
  private String password;

  /**
   * First name of the user.
   */
  @NotBlank(message = "first name cannot be blank")
  @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain alphabets")
  private String firstName;

  /**
   * Last name of the user.
   */
  @NotBlank(message = "last name cannot be blank")
  @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain alphabets")
  private String lastName;
  /**
   * Address of the user.
   */
  private String address;
  /**
   * Wallet Balance of the user.
   */
  private Double walletBalance;

}
