package com.food.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to be thrown when a service is unavailable.
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {

  /**
   * Constructor for creating a ServiceUnavailableException with a custom message.
   *
   * @param message the custom message for the exception.
   */
  public ServiceUnavailableException(final String message) {
    super(message);
  }
}
