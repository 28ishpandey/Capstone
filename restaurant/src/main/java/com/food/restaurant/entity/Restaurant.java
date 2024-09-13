package com.food.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a restaurant.
 * Contains details such as email, contact number, name, address, timings, image, status, and the owner ID.
 * This entity is mapped to a database table that stores these details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

  /**
   * The unique identifier for the restaurant.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The email associated with the restaurant. Must be unique and end with "gmail.com".
   */
  @Email(regexp = ".+@gmail\\.com", message = "Email should end with gmail.com")
  @Column(unique = true, nullable = false)
  private String email;

  /**
   * The contact number of the restaurant. Must be exactly 10 digits.
   */
  @Column(length = 10, nullable = false)
  private String contactNumber;

  /**
   * The encrypted password for managing the restaurant account.
   */
  @Column(nullable = false)
  private String password;

  /**
   * The name of the restaurant.
   */
  @Column(nullable = false)
  private String name;

  /**
   * The address of the restaurant.
   */
  @Column(nullable = false)
  private String address;

  /**
   * The operating hours of the restaurant in "HH:mm-HH:mm" format.
   */
  @Column(nullable = false)
  private String timings;

  /**
   * The image of the restaurant stored in binary format.
   */
  @Lob
  private byte[] image;

  /**
   * The status indicating whether the restaurant is open or closed.
   */
  @Column(nullable = false)
  private Boolean isOpen;

  /**
   * The unique identifier of the restaurant owner who owns this restaurant.
   */
  @Column(nullable = false)
  private Long restaurantOwnerId;
}
