package com.food.user.exception;

/**
 * Exception thrown when credentials provided are invalid.
 * Extends {@link RuntimeException}.
 */
public class InvalidCredentials extends RuntimeException {

  /**
   * Constructs a new {@code InvalidCredentials} exception with a default detail message.
   * The default message is "Invalid credentials".
   */
  public InvalidCredentials() {
    super("Invalid credentials");
  }
}

