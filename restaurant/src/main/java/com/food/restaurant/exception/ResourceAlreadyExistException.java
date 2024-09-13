package com.food.restaurant.exception;

/**
 * Custom exception thrown when a resource already exists.
 */
public class ResourceAlreadyExistException extends RuntimeException {

  /**
   * Constructs a ResourceAlreadyExistException with the specified message.
   *
   * @param message the detail message.
   */
  public ResourceAlreadyExistException(String message) {
    super(message);
  }
}
