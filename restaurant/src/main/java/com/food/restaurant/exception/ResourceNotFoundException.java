package com.food.restaurant.exception;

/**
 * Custom exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Constructs a ResourceNotFoundException with the specified message.
   *
   * @param message the detail message.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
