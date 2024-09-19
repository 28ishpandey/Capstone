package com.food.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

/**
 * Entity representing a user in the system.
 * Maps to the 'Users' table in the database.
 */
@Data
@Entity
public final class Users {

  /**
   * Default wallet balance for new users.
   */
  private static final double DEFAULT_WALLET_BALANCE = 1000.0;

  /**
   * Unique identifier of the user. Auto-generated by the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  /**
   * Email address of the user. Must be unique and cannot be null.
   */
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * Contact number of the user. Must be unique and cannot be null.
   */
  @Column(nullable = false, unique = true)
  private String contactNumber;

  /**
   * Password for the user's account. Cannot be null.
   */
  @Column(nullable = false)
  private String password;

  /**
   * First name of the user. Cannot be null.
   */
  @Column(nullable = false)
  private String firstName;

  /**
   * Last name of the user. Cannot be null.
   */
  @Column(nullable = false)
  private String lastName;

  /**
   * Wallet balance of the user. Initialized to DEFAULT_WALLET_BALANCE by default.
   */
  @Column(nullable = false)
  private Double walletBalance = DEFAULT_WALLET_BALANCE;

  /**
   * Address of the user.
   */
  private String address;

  /**
   * Checks if this Users object is equal to another object.
   *
   * @param o The object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Users users = (Users) o;
    return Objects.equals(userId, users.userId)
      && Objects.equals(email, users.email)
      && Objects.equals(contactNumber, users.contactNumber)
      && Objects.equals(password, users.password)
      && Objects.equals(firstName, users.firstName)
      && Objects.equals(lastName, users.lastName)
      && Objects.equals(walletBalance, users.walletBalance)
      && Objects.equals(address, users.address);
  }

  /**
   * Generates a hash code for this Users object.
   *
   * @return The hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(userId, email, contactNumber, password, firstName, lastName, walletBalance, address);
  }
}
