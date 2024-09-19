package com.food.order.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {

  @Test
  void testInvalidInputException() {
    String message = "Invalid input";
    InvalidInputException exception = new InvalidInputException(message);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testResourceAlreadyExistException() {
    String message = "Resource already exists";
    ResourceAlreadyExistException exception = new ResourceAlreadyExistException(message);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testResourceNotFoundException() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);
    assertEquals(message, exception.getMessage());
  }
}
