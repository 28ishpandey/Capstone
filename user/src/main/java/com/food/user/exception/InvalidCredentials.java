package com.food.user.exception;

/**
 * Custom exception for invalid credentials.
 */
public class InvalidCredentials extends RuntimeException {
  public InvalidCredentials() {
    super("Invalid credentials");
  }
}
