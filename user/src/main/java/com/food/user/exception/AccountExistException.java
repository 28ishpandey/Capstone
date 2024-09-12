package com.food.user.exception;

/**
 * Exception thrown when an attempt is made to create an account that already exists.
 * Extends {@link RuntimeException}.
 */
public class AccountExistException extends RuntimeException {

  /**
   * Constructs a new {@code AccountExistException} with a default detail message.
   * The default message is "Account already exists".
   */
  public AccountExistException() {
    super("Account already exists");
  }
}
