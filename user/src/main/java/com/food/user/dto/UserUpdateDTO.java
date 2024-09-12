package com.food.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  @Pattern(regexp = ".*\\.com$", message = "Email should end with 'domain.com'")
  private String email;

  @NotBlank(message = "contact number cannot be blank")
  @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
  private String contactNumber;

  @NotBlank(message = "first name cannot be blank")
  private String firstName;

  @NotBlank(message = "last name cannot be blank")
  private String lastName;

  private String address;

  @NotNull(message = "Wallet balance cannot be null")
  @Min(value = 0, message = "Wallet balance cannot be negative")
  private Double walletBalance;
}