package com.food.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a restaurant owner.
 * Contains basic personal information such as first name, last name, email, contact number, and password.
 * This entity is mapped to a database table with fields that store these details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOwner {

  /**
   * The unique identifier for the restaurant owner.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The first name of the restaurant owner.
   */
  private String firstName;

  /**
   * The last name of the restaurant owner.
   */
  private String lastName;

  /**
   * The email of the restaurant owner. Must be unique and end with "gmail.com".
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  @Column(unique = true, nullable = false)
  private String email;

  /**
   * The contact number of the restaurant owner. Must be exactly 10 digits.
   */
  @Column(length = 10, nullable = false)
  private String contactNumber;

  /**
   * The encrypted password of the restaurant owner.
   */
  @Column(nullable = false)
  private String password;
}
