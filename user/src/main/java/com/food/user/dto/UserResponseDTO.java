package com.food.user.dto;


import lombok.Data;


/**
 * Data Transfer Object (DTO) for representing a user in responses.
 * Contains details of a user returned from the server.
 */
@Data
public class UserResponseDTO {

  /**
   * Unique identifier of the user.
   */
  private Long userId;

  /**
   * Email address of the user.
   */
  private String email;

  /**
   * Contact number of the user.
   */
  private String contactNumber;

  /**
   * First name of the user.
   */
  private String firstName;

  /**
   * Last name of the user.
   */
  private String lastName;

  /**
   * Wallet balance of the user.
   */
  private Double walletBalance;
  /**
   * Address of the user.
   */
  private String address;
}
