package com.food.restaurant.service;

import com.food.restaurant.exception.GlobalExceptionHandler;
import com.food.restaurant.exception.InvalidInputException;
import com.food.restaurant.exception.ResourceAlreadyExistException;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.exception.RestaurantExistException;
import com.food.restaurant.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlingTests {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void testInvalidInputException() {
    String errorMessage = "Invalid input";
    InvalidInputException exception = new InvalidInputException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

  @Test
  void testResourceAlreadyExistException() {
    String errorMessage = "Resource already exists";
    ResourceAlreadyExistException exception = new ResourceAlreadyExistException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

  @Test
  void testResourceNotFoundException() {
    String errorMessage = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

  @Test
  void testRestaurantExistException() {
    RestaurantExistException exception = new RestaurantExistException();
    assertEquals("Account already exists", exception.getMessage());
  }

  @Test
  void testRestaurantNotFoundException() {
    RestaurantNotFoundException exception = new RestaurantNotFoundException();
    assertEquals("Account not found", exception.getMessage());
  }

  @Test
  void testHandleRestaurantExistsException() {
    RestaurantExistException ex = new RestaurantExistException();

    GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleRestaurantExistsException(ex);

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    assertEquals("Account already exists", response.getMessage());
  }

  @Test
  void testHandleRestaurantNotFoundException() {
    RestaurantNotFoundException ex = new RestaurantNotFoundException();

    GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleRestaurantNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals("Account not found", response.getMessage());
  }

  @Test
  void testHandleResourceNotFoundException() {
    ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

    GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleResourceNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals("Resource not found", response.getMessage());
  }

  @Test
  void testHandleResourceAlreadyExistsException() {
    ResourceAlreadyExistException ex = new ResourceAlreadyExistException("Resource already exists");

    GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleResourceAlreadyExistsException(ex);

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    assertEquals("Resource already exists", response.getMessage());
  }

  @Test
  void testHandleInvalidInputException() {
    InvalidInputException ex = new InvalidInputException("Invalid input");

    GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleInvalidInputException(ex);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals("Invalid input", response.getMessage());
  }

  @Test
  void testErrorResponse() {
    GlobalExceptionHandler.ErrorResponse errorResponse = new GlobalExceptionHandler.ErrorResponse(400, "Bad Request");

    assertEquals(400, errorResponse.getStatus());
    assertEquals("Bad Request", errorResponse.getMessage());

    errorResponse.setStatus(404);
    errorResponse.setMessage("Not Found");

    assertEquals(404, errorResponse.getStatus());
    assertEquals("Not Found", errorResponse.getMessage());
  }
}
