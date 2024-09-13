package com.food.restaurant.exception;

/**
 * Custom exception thrown when a restaurant account already exists.
 */
public class RestaurantExistException extends RuntimeException {

  /**
   * Constructs a RestaurantExistException with a default message.
   */
  public RestaurantExistException() {
    super("Account already exists");
  }
}
