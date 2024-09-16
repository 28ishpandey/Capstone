package com.food.order.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling various exceptions across the application.
 * Provides centralized handling of exceptions and returns custom error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles validation exceptions thrown when method argument validation fails.
   *
   * @param ex the {@link MethodArgumentNotValidException} thrown when a validation error occurs.
   * @return a custom {@link ErrorResponse} with an appropriate HTTP status code and error message.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = "Validation failed: " + ex.getFieldError().getDefaultMessage();
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
  }

  /**
   * Handles exceptions when a requested resource is not found.
   *
   * @param ex the {@link ResourceNotFoundException} thrown when the requested resource cannot be found.
   * @return a custom {@link ErrorResponse} with HTTP status 404 and an error message.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  /**
   * Handles exceptions when a resource already exists in the system.
   *
   * @param ex the {@link ResourceAlreadyExistException} thrown when trying to create a resource that already exists.
   * @return a custom {@link ErrorResponse} with HTTP status 409 (Conflict) and an error message.
   */
  @ExceptionHandler(ResourceAlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleResourceAlreadyExistsException(ResourceAlreadyExistException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  /**
   * Handles exceptions when invalid input is provided.
   *
   * @param ex the {@link InvalidInputException} thrown when input data is invalid.
   * @return a custom {@link ErrorResponse} with HTTP status 400 (Bad Request) and an error message.
   */
  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleInvalidInputException(InvalidInputException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  /**
   * Handles Feign client exceptions, typically thrown when a service is unavailable or unresponsive.
   *
   * @param ex the {@link FeignException} thrown when there is an issue with a Feign client call.
   * @return a custom {@link ErrorResponse} with HTTP status 503 (Service Unavailable) and an error message.
   */
  @ExceptionHandler(FeignException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  @ResponseBody
  public ErrorResponse handleFeignException(FeignException ex) {
    return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service temporarily unavailable");
  }

  /**
   * Custom error response structure for returning error details in the API response.
   */
  public static class ErrorResponse {
    /**
     * The HTTP status code for the error.
     */
    private int status;

    /**
     * The error message providing details about the error.
     */
    private String message;

    /**
     * Constructor for creating an error response.
     *
     * @param status  the HTTP status code.
     * @param message the error message.
     */
    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }

    /**
     * Gets the HTTP status code for the error.
     *
     * @return the HTTP status code.
     */
    public int getStatus() {
      return status;
    }

    /**
     * Sets the HTTP status code for the error.
     *
     * @param status the HTTP status code.
     */
    public void setStatus(int status) {
      this.status = status;
    }

    /**
     * Gets the error message providing details about the error.
     *
     * @return the error message.
     */
    public String getMessage() {
      return message;
    }

    /**
     * Sets the error message providing details about the error.
     *
     * @param message the error message.
     */
    public void setMessage(String message) {
      this.message = message;
    }
  }
}
