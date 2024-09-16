package com.food.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler handles various exceptions across the application.
 * It returns appropriate HTTP status codes and error messages to the client.
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
  public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.toList());

    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
  }

  /**
   * Handles RestaurantExistException, which is thrown when attempting to create a restaurant
   * that already exists.
   *
   * @param ex the RestaurantExistException.
   * @return ErrorResponse containing a conflict status and the exception message.
   */
  @ExceptionHandler(RestaurantExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleRestaurantExistsException(RestaurantExistException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Handles RestaurantNotFoundException, which is thrown when a restaurant is not found.
   *
   * @param ex the RestaurantNotFoundException.
   * @return ErrorResponse containing a not found status and the exception message.
   */
  @ExceptionHandler(RestaurantNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  /**
   * Handles ResourceNotFoundException, which is thrown when a requested resource is not found.
   *
   * @param ex the ResourceNotFoundException.
   * @return ErrorResponse containing a not found status and the exception message.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  /**
   * Handles ResourceAlreadyExistException, which is thrown when attempting to create a resource
   * that already exists.
   *
   * @param ex the ResourceAlreadyExistException.
   * @return ErrorResponse containing a conflict status and the exception message.
   */
  @ExceptionHandler(ResourceAlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleResourceAlreadyExistsException(ResourceAlreadyExistException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Handles InvalidInputException, which is thrown when invalid input is provided.
   *
   * @param ex the InvalidInputException.
   * @return ErrorResponse containing a bad request status and the exception message.
   */
  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleInvalidInputException(InvalidInputException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  /**
   * Represents a standard error response with HTTP status, message, and optional validation errors.
   */
  public static class ErrorResponse {
    private int status;
    private String message;
    private List<String> errors;

    /**
     * Constructs an {@code ErrorResponse} for validation errors with a status, message, and list of errors.
     *
     * @param status  the HTTP status code
     * @param message the error message
     * @param errors  the list of validation errors
     */
    public ErrorResponse(int status, String message, List<String> errors) {
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
    public void setErrors(List<String> errors) {
      this.errors = errors;
    }
  }
}
