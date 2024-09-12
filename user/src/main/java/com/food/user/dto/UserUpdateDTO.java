package com.food.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for updating user information.
 * <p>
 * This class uses Lombok annotations to automatically generate getter, setter,
 * toString, equals, and hashCode methods, as well as no-argument and all-arguments
 * constructors.
 * </p>
 *
 * @see lombok.Data
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

  /**
   * The email address of the user.
   * <p>
   * Must be a valid email format and end with ".com".
   * </p>
   */
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  @Pattern(regexp = ".*\\.com$", message = "Email should end with '.com'")
  private String email;

  /**
   * The contact number of the user.
   * <p>
   * Must be exactly 10 digits.
   * </p>
   */
  @NotBlank(message = "Contact number cannot be blank")
  @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
  private String contactNumber;

  /**
   * The first name of the user.
   */
  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  /**
   * The last name of the user.
   */
  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  /**
   * The address of the user.
   */
  private String address;

  /**
   * The wallet balance of the user.
   * <p>
   * Cannot be null and must be non-negative.
   * </p>
   */
  @NotNull(message = "Wallet balance cannot be null")
  @Min(value = 0, message = "Wallet balance cannot be negative")
  private Double walletBalance;
}