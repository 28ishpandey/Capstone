package com.food.user.exception;

/**
 * Custom exception for account not found.
 */
public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException() {
    super("Account not found");
  }
}
