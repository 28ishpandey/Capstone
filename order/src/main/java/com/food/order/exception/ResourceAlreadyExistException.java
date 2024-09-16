package com.food.order.exception;

/**
 * Custom exception thrown when a resource already exists in the system.
 */
public class ResourceAlreadyExistException extends RuntimeException {
  /**
   * Constructor to create an instance of {@link ResourceAlreadyExistException}.
   *
   * @param message the error message indicating that the resource already exists.
   */
  public ResourceAlreadyExistException(String message) {
    super(message);
  }
}