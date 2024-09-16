package com.food.order.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) to represent user details in responses.
 */
@Data
public class UserResponseDTO {
  /**
   * The ID of the user.
   */
  private Long userId;

  /**
   * The email address of the user.
   */
  private String email;

  /**
   * The contact number of the user.
   */
  private String contactNumber;

  /**
   * The first name of the user.
   */
  private String firstName;

  /**
   * The last name of the user.
   */
  private String lastName;

  /**
   * The wallet balance of the user.
   */
  private Double walletBalance;

  /**
   * The address of the user.
   */
  private String address;
}

