package com.food.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler handles various exceptions across the application.
 * It returns appropriate HTTP status codes and error messages to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles MethodArgumentNotValidException, which is thrown when validation on an argument fails.
   *
   * @param ex the MethodArgumentNotValidException thrown when validation fails.
   * @return ErrorResponse containing a bad request status and the validation error message.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = "Validation failed: " + ex.getFieldError().getDefaultMessage();
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
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
   * Inner class representing an error response that will be returned in the case of an exception.
   */
  public static class ErrorResponse {
    private int status;
    private String message;

    /**
     * Constructs an ErrorResponse with the specified status and message.
     *
     * @param status  the HTTP status code.
     * @param message the error message.
     */
    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the status code.
     */
    public int getStatus() {
      return status;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status the status code.
     */
    public void setStatus(int status) {
      this.status = status;
    }

    /**
     * Returns the error message.
     *
     * @return the error message.
     */
    public String getMessage() {
      return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message.
     */
    public void setMessage(String message) {
      this.message = message;
    }
  }
}
