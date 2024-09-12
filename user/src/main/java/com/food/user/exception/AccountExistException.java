package com.food.user.exception;

public class AccountExistException extends RuntimeException {
  public AccountExistException() {
    super("Account already exists");
  }
}
