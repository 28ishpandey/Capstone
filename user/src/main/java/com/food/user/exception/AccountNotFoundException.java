package com.food.user.exception;

/**
 * Exception thrown when an account is not found.
 * Extends {@link RuntimeException}.
 */
public class AccountNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code AccountNotFoundException} with a default detail message.
   * The default message is "Account not found".
   */
  public AccountNotFoundException() {
    super("Account not found");
  }
}
