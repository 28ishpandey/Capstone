package com.food.restaurant.exception;


/**
 * Exception thrown when a restaurant cannot be found.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate that the requested
 * restaurant does not exist in the system. It is typically used when a restaurant lookup
 * fails.
 * </p>
 */
public class RestaurantNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code RestaurantNotFoundException} with the default message
   * "Account not found".
   */
  public RestaurantNotFoundException() {
    super("Account not found");
  }
}