package com.food.restaurant.exception;

/**
 * Custom exception thrown when invalid input is provided.
 */
public class InvalidInputException extends RuntimeException {

  /**
   * Constructs an InvalidInputException with the specified message.
   *
   * @param message the detail message.
   */
  public InvalidInputException(String message) {
    super(message);
  }
}
