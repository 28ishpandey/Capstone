package com.food.restaurant.controller;

import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.dto.RestaurantDetailsDTO;
import com.food.restaurant.dto.RestaurantImageDTO;
import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.service.RestaurantService;
import com.food.restaurant.util.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

  @Mock
  private RestaurantService restaurantService;

  @InjectMocks
  private RestaurantController restaurantController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRestaurant_Success() {
    RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
    MultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO("Restaurant created successfully"), HttpStatus.CREATED);

    when(restaurantService.createRestaurant(restaurantInDTO, image)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = restaurantController.createRestaurant(restaurantInDTO, image);

    assertEquals(expectedResponse, response);
    verify(restaurantService).createRestaurant(restaurantInDTO, image);
  }


  @Test
  void deleteRestaurant_Success() {
    Long id = 1L;
    String ownerPassword = "password";
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO("Restaurant deleted successfully"), HttpStatus.OK);

    when(restaurantService.deleteRestaurant(id, ownerPassword)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = restaurantController.deleteRestaurant(id, ownerPassword);

    assertEquals(expectedResponse, response);
    verify(restaurantService).deleteRestaurant(id, ownerPassword);
  }

  @Test
  void uploadRestaurantImage_Success() {
    RestaurantImageDTO restaurantImageDTO = new RestaurantImageDTO();
    ResponseEntity<String> expectedResponse = new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);

    when(restaurantService.uploadRestaurantImage(restaurantImageDTO)).thenReturn(expectedResponse);

    ResponseEntity<String> response = restaurantController.uploadRestaurantImage(restaurantImageDTO);

    assertEquals(expectedResponse, response);
    verify(restaurantService).uploadRestaurantImage(restaurantImageDTO);
  }

  @Test
  void getRestaurantImage_Success() {
    Long restaurantId = 1L;
    byte[] imageBytes = "test image content".getBytes();
    ResponseEntity<byte[]> expectedResponse = new ResponseEntity<>(imageBytes, HttpStatus.OK);

    when(restaurantService.getRestaurantImage(restaurantId)).thenReturn(expectedResponse);

    ResponseEntity<byte[]> response = restaurantController.getRestaurantImage(restaurantId);

    assertEquals(expectedResponse, response);
    verify(restaurantService).getRestaurantImage(restaurantId);
  }

  @Test
  void updateRestaurantImage_Success() {
    RestaurantImageDTO restaurantImageDTO = new RestaurantImageDTO();
    ResponseEntity<MessageDTO> expectedResponse = new ResponseEntity<>(new MessageDTO("Image updated successfully"), HttpStatus.OK);

    when(restaurantService.updateRestaurantImage(restaurantImageDTO)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = restaurantController.updateRestaurantImage(restaurantImageDTO);

    assertEquals(expectedResponse, response);
    verify(restaurantService).updateRestaurantImage(restaurantImageDTO);
  }

  @Test
  void deleteRestaurantImage_Success() {
    Long restaurantId = 1L;
    ResponseEntity<String> expectedResponse = new ResponseEntity<>("Image deleted successfully", HttpStatus.NO_CONTENT);

    when(restaurantService.deleteRestaurantImage(restaurantId)).thenReturn(expectedResponse);

    ResponseEntity<String> response = restaurantController.deleteRestaurantImage(restaurantId);

    assertEquals(expectedResponse, response);
    verify(restaurantService).deleteRestaurantImage(restaurantId);
  }

  @Test
  void setRestaurantStatus_Success() {
    Long id = 1L;
    Boolean isOpen = true;
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO("Restaurant status updated successfully"), HttpStatus.OK);

    when(restaurantService.setRestaurantStatus(id, isOpen)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = restaurantController.setRestaurantStatus(id, isOpen);

    assertEquals(expectedResponse, response);
    verify(restaurantService).setRestaurantStatus(id, isOpen);
  }

  @Test
  void getAllFoodItemsByRestaurant_Success() {
    Long restaurantId = 1L;
    List<CategoryOutDTO> categories = Arrays.asList(new CategoryOutDTO(), new CategoryOutDTO());
    ResponseEntity<List<CategoryOutDTO>> expectedResponse = new ResponseEntity<>(categories, HttpStatus.OK);

    when(restaurantService.getAllFoodItemsByRestaurant(restaurantId)).thenReturn(expectedResponse);

    ResponseEntity<List<CategoryOutDTO>> response = restaurantController.getAllFoodItemsByRestaurant(restaurantId);

    assertEquals(expectedResponse, response);
    verify(restaurantService).getAllFoodItemsByRestaurant(restaurantId);
  }

  @Test
  void getAllFoodItemsOfRestaurant_Success() {
    Long restaurantId = 1L;
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(restaurantService.getAllFoodItemsOfRestaurant(restaurantId)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = restaurantController.getAllFoodItemsOfRestaurant(restaurantId);

    assertEquals(expectedResponse, response);
    verify(restaurantService).getAllFoodItemsOfRestaurant(restaurantId);
  }

  @Test
  void getAllRestaurantsWithDetails_Success() {
    List<RestaurantDetailsDTO> restaurantDetails = Arrays.asList(new RestaurantDetailsDTO(), new RestaurantDetailsDTO());
    ResponseEntity<List<RestaurantDetailsDTO>> expectedResponse = new ResponseEntity<>(restaurantDetails, HttpStatus.OK);

    when(restaurantService.getAllRestaurantsWithDetails()).thenReturn(expectedResponse);

    ResponseEntity<List<RestaurantDetailsDTO>> response = restaurantController.getAllRestaurantsWithDetails();

    assertEquals(expectedResponse, response);
    verify(restaurantService).getAllRestaurantsWithDetails();
  }
}
