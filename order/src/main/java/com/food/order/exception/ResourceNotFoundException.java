package com.food.order.exception;

/**
 * Custom exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
  /**
   * Constructor to create an instance of {@link ResourceNotFoundException}.
   *
   * @param message the error message indicating that the resource was not found.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}