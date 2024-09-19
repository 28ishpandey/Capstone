package com.food.restaurant.exception;


/**
 * Exception thrown when invalid credentials are provided.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate that the credentials
 * provided for authentication are invalid. It is typically used in scenarios where a user
 * login fails due to incorrect credentials.
 * </p>
 */
public class InvalidCredentials extends RuntimeException {

  /**
   * Constructs a new {@code InvalidCredentials} with the default message
   * "Invalid credentials".
   */
  public InvalidCredentials() {
    super("Invalid credentials");
  }
}
