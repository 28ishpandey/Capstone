package com.food.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling exceptions across the whole application.
 * Provides centralized exception handling for various types of exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link MethodArgumentNotValidException} exceptions which occur due to validation failures.
   * Collects all field-specific validation errors and returns them in the response.
   *
   * @param ex the {@code MethodArgumentNotValidException} to handle
   * @return an {@link ErrorResponse} containing the error details for all fields that failed validation
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleValidationExceptions(final MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.toList());

    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
  }

  /**
   * Handles {@link AccountExistException} exceptions which occur when an account already exists.
   * Returns a response with HTTP status code 409 (Conflict) and the exception message.
   *
   * @param ex the {@code AccountExistException} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(AccountExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleAccountExistsException(final AccountExistException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Handles {@link AccountNotFoundException} exceptions which occur when an account is not found.
   * Returns a response with HTTP status code 409 (Conflict) and the exception message.
   *
   * @param ex the {@code AccountNotFoundException} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleAccountNotFoundException(final AccountNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  /**
   * Handles {@link InvalidCredentials} exceptions which occur when the credentials are wrong.
   * Returns a response with HTTP status code 409 (Conflict) and the exception message.
   *
   * @param ex the {@code InvalidCredentials} to handle
   * @return an {@link ErrorResponse} containing the error details
   */
  @ExceptionHandler(InvalidCredentials.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleInvalidCredentialsException(final InvalidCredentials ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Represents a standard error response with HTTP status, message, and optional validation errors.
   */
  public static class ErrorResponse {
    /**
     * The HTTP status code of the error response.
     */
    private int status;

    /**
     * The error message of the error response.
     */
    private String message;

    /**
     * The list of validation errors, if present.
     */
    private List<String> errors;

    /**
     * Constructs an {@code ErrorResponse} for validation errors with a status, message, and list of errors.
     *
     * @param status  the HTTP status code
     * @param message the error message
     * @param errors  the list of validation errors
     */
    public ErrorResponse(final int status, final String message, final List<String> errors) {
      this.status = status;
      this.message = message;
      this.errors = errors;
    }

    /**
     * Constructs an {@code ErrorResponse} for non-validation exceptions with a status and message.
     *
     * @param status  the HTTP status code
     * @param message the error message
     */
    public ErrorResponse(final int status, final String message) {
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
    public void setStatus(final int status) {
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
    public void setMessage(final String message) {
      this.message = message;
    }

    /**
     * Gets the list of validation errors, if present.
     *
     * @return the list of validation errors
     */
    public List<String> getErrors() {
      return errors;
    }

    /**
     * Sets the list of validation errors.
     *
     * @param errors the list of validation errors to set
     */
    public void setErrors(final List<String> errors) {
      this.errors = errors;
    }
  }
}
