package com.food.order.exception;

/**
 * Custom exception thrown when invalid input is provided by the user.
 */
public class InvalidInputException extends RuntimeException {
  /**
   * Constructor to create an instance of {@link InvalidInputException}.
   *
   * @param message the error message associated with the invalid input.
   */
  public InvalidInputException(final String message) {
    super(message);
  }
}
