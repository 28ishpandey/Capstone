package com.food.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling exceptions across the whole application.
 * Provides centralized exception handling for various types of exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link MethodArgumentNotValidException} exceptions.
   * Returns a response with HTTP status code 400 (Bad Request) and a detailed validation error message.
   *
   * @param ex the {@code MethodArgumentNotValidException} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = "Validation failed";
    if (ex.getFieldError() != null && ex.getFieldError().getDefaultMessage() != null) {
      errorMessage += ": " + ex.getFieldError().getDefaultMessage();
    }
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
  }

  /**
   * Handles {@link AccountExistException} exceptions.
   * Returns a response with HTTP status code 409 (Conflict) and the exception message.
   *
   * @param ex the {@code AccountExistException} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(AccountExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleAccountExistsException(AccountExistException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Handles {@link AccountNotFoundException} exceptions.
   * Returns a response with HTTP status code 409 (Conflict) and the exception message.
   *
   * @param ex the {@code AccountNotFoundException} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleAccountNotFoundException(AccountNotFoundException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Represents an error response containing status and message details.
   */
  public static class ErrorResponse {
    private int status;
    private String message;

    /**
     * Constructs an {@code ErrorResponse} with the specified status and message.
     *
     * @param status  the HTTP status code
     * @param message the error message
     */
    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }

    /**
     * Gets the HTTP status code of the error response.
     *
     * @return the status code
     */
    public int getStatus() {
      return status;
    }

    /**
     * Sets the HTTP status code of the error response.
     *
     * @param status the status code to set
     */
    public void setStatus(int status) {
      this.status = status;
    }

    /**
     * Gets the error message of the error response.
     *
     * @return the error message
     */
    public String getMessage() {
      return message;
    }

    /**
     * Sets the error message of the error response.
     *
     * @param message the error message to set
     */
    public void setMessage(String message) {
      this.message = message;
    }
  }
}

