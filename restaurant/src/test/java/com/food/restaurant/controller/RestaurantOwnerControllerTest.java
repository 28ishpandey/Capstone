package com.food.restaurant.controller;

import com.food.restaurant.dto.ForgotPasswordDTO;
import com.food.restaurant.dto.LoginDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.service.RestaurantOwnerService;
import com.food.restaurant.util.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantOwnerControllerTest {

  @Mock
  private RestaurantOwnerService ownerService;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private RestaurantOwnerController ownerController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRestaurantOwner_Success() {
    RestaurantOwnerInDTO ownerDto = new RestaurantOwnerInDTO();
    ownerDto.setEmail("test@example.com");
    ResponseEntity<MessageDTO> expectedResponse = new ResponseEntity<>(new MessageDTO("Owner created"), HttpStatus.CREATED);
    when(ownerService.createRestaurantOwner(ownerDto)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = ownerController.createRestaurantOwner(ownerDto);

    assertEquals(expectedResponse, response);
    verify(ownerService).createRestaurantOwner(ownerDto);
  }

  @Test
  void deleteRestaurantOwner_Success() {
    Long id = 1L;
    ResponseEntity<MessageDTO> expectedResponse = new ResponseEntity<>(new MessageDTO("Owner deleted"), HttpStatus.OK);
    when(ownerService.deleteRestaurantOwner(id)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = ownerController.deleteRestaurantOwner(id);

    assertEquals(expectedResponse, response);
    verify(ownerService).deleteRestaurantOwner(id);
  }

  @Test
  void deleteRestaurantOwner_NotFound() {
    Long id = 999L;
    when(ownerService.deleteRestaurantOwner(id)).thenReturn(new ResponseEntity<>(new MessageDTO("Not found"), HttpStatus.NOT_FOUND));

    ResponseEntity<MessageDTO> response = ownerController.deleteRestaurantOwner(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getRestaurantOwner_Success() {
    Long id = 1L;
    RestaurantOwnerOutDTO ownerDto = new RestaurantOwnerOutDTO();
    ownerDto.setId(id);
    ResponseEntity<RestaurantOwnerOutDTO> expectedResponse = new ResponseEntity<>(ownerDto, HttpStatus.OK);
    when(ownerService.getRestaurantOwner(id)).thenReturn(expectedResponse);

    ResponseEntity<RestaurantOwnerOutDTO> response = ownerController.getRestaurantOwner(id);

    assertEquals(expectedResponse, response);
    verify(ownerService).getRestaurantOwner(id);
  }

  @Test
  void getRestaurantOwner_NotFound() {
    Long id = 999L;
    when(ownerService.getRestaurantOwner(id)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    ResponseEntity<RestaurantOwnerOutDTO> response = ownerController.getRestaurantOwner(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getAllRestaurantOwners_Success() {
    List<RestaurantOwnerOutDTO> owners = Arrays.asList(new RestaurantOwnerOutDTO(), new RestaurantOwnerOutDTO());
    ResponseEntity<List<RestaurantOwnerOutDTO>> expectedResponse = new ResponseEntity<>(owners, HttpStatus.OK);
    when(ownerService.getAllRestaurantOwners()).thenReturn(expectedResponse);

    ResponseEntity<List<RestaurantOwnerOutDTO>> response = ownerController.getAllRestaurantOwners();

    assertEquals(expectedResponse, response);
    assertEquals(2, response.getBody().size());
    verify(ownerService).getAllRestaurantOwners();
  }

  @Test
  void getAllRestaurantOwners_EmptyList() {
    List<RestaurantOwnerOutDTO> emptyList = List.of();
    ResponseEntity<List<RestaurantOwnerOutDTO>> expectedResponse = new ResponseEntity<>(emptyList, HttpStatus.OK);
    when(ownerService.getAllRestaurantOwners()).thenReturn(expectedResponse);

    ResponseEntity<List<RestaurantOwnerOutDTO>> response = ownerController.getAllRestaurantOwners();

    assertEquals(expectedResponse, response);
    assertTrue(response.getBody().isEmpty());
  }

  @Test
  void login_Success() {
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail("test@example.com");
    loginDto.setPassword("password");
    RestaurantOwnerOutDTO ownerDto = new RestaurantOwnerOutDTO();
    ResponseEntity<RestaurantOwnerOutDTO> expectedResponse = new ResponseEntity<>(ownerDto, HttpStatus.OK);
    when(ownerService.login(loginDto)).thenReturn(expectedResponse);

    ResponseEntity<RestaurantOwnerOutDTO> response = ownerController.login(loginDto);

    assertEquals(expectedResponse, response);
    verify(ownerService).login(loginDto);
  }

  @Test
  void login_InvalidCredentials() {
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail("test@example.com");
    loginDto.setPassword("wrongpassword");
    when(ownerService.login(loginDto)).thenThrow(new ResourceNotFoundException("Invalid credentials"));

    assertThrows(ResourceNotFoundException.class, () -> ownerController.login(loginDto));
  }


  @Test
  void getRestaurantsByOwnerId_NoRestaurants() {
    Long ownerId = 1L;
    when(ownerService.getRestaurantsByOwnerId(ownerId)).thenThrow(new ResourceNotFoundException("No restaurants found"));

    assertThrows(ResourceNotFoundException.class, () -> ownerController.getRestaurantsByOwnerId(ownerId));
  }

  @Test
  void forgotPassword_Success() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("test@example.com");
    ResponseEntity<MessageDTO> expectedResponse = new ResponseEntity<>(new MessageDTO("Password reset email sent"), HttpStatus.OK);

    ResponseEntity<MessageDTO> response = ownerController.forgotPassword(forgotPasswordDTO);

    assertEquals(expectedResponse, response);
    verify(ownerService).forgotPassword(forgotPasswordDTO);
  }

  @Test
  void forgotPassword_EmailNotFound() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("nonexistent@example.com");
    doThrow(new ResourceNotFoundException("Email not found")).when(ownerService).forgotPassword(forgotPasswordDTO);

    assertThrows(ResourceNotFoundException.class, () -> ownerController.forgotPassword(forgotPasswordDTO));
  }
}