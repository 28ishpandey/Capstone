package com.food.restaurant.controller;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.FoodItemImageDTO;
import com.food.restaurant.dto.FoodItemInDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.service.FoodItemService;
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

class FoodItemControllerTest {

  @Mock
  private FoodItemService foodItemService;

  @InjectMocks
  private FoodItemController foodItemController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createFoodItem_Success() {
    FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
    MultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.FOOD_ITEM_CREATED_SUCCESSFULLY), HttpStatus.CREATED);

    when(foodItemService.createFoodItem(foodItemInDTO, image)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = foodItemController.createFoodItem(foodItemInDTO, image);

    assertEquals(expectedResponse, response);
    verify(foodItemService).createFoodItem(foodItemInDTO, image);
  }

  @Test
  void getAllFoodItems_Success() {
    Long categoryId = 1L;
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(foodItemService.getAllFoodItemsForCategory(categoryId)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemController.getAllFoodItems(categoryId);

    assertEquals(expectedResponse, response);
    verify(foodItemService).getAllFoodItemsForCategory(categoryId);
  }


  @Test
  void deleteFoodItem_Success() {
    Long id = 1L;
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.FOOD_ITEM_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);

    when(foodItemService.deleteFoodItem(id)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = foodItemController.deleteFoodItem(id);

    assertEquals(expectedResponse, response);
    verify(foodItemService).deleteFoodItem(id);
  }

  @Test
  void uploadFoodItemImage_Success() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    ResponseEntity<String> expectedResponse = new ResponseEntity<>(Constants.IMAGE_ADDED_SUCCESSFULLY, HttpStatus.OK);

    when(foodItemService.uploadFoodItemImage(foodItemImageDTO)).thenReturn(expectedResponse);

    ResponseEntity<String> response = foodItemController.uploadFoodItemImage(foodItemImageDTO);

    assertEquals(expectedResponse, response);
    verify(foodItemService).uploadFoodItemImage(foodItemImageDTO);
  }

  @Test
  void getFoodItemImage_Success() {
    Long foodItemId = 1L;
    byte[] imageBytes = "test image content".getBytes();
    ResponseEntity<byte[]> expectedResponse = new ResponseEntity<>(imageBytes, HttpStatus.OK);

    when(foodItemService.getFoodItemImage(foodItemId)).thenReturn(expectedResponse);

    ResponseEntity<byte[]> response = foodItemController.getFoodItemImage(foodItemId);

    assertEquals(expectedResponse, response);
    verify(foodItemService).getFoodItemImage(foodItemId);
  }

  @Test
  void updateFoodItemImage_Success() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.IMAGE_UPDATED_SUCCESSFULLY), HttpStatus.OK);

    when(foodItemService.updateFoodItemImage(foodItemImageDTO)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = foodItemController.updateFoodItemImage(foodItemImageDTO);

    assertEquals(expectedResponse, response);
    verify(foodItemService).updateFoodItemImage(foodItemImageDTO);
  }

  @Test
  void deleteFoodItemImage_Success() {
    Long foodItemId = 1L;
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.IMAGE_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);

    when(foodItemService.deleteFoodItemImage(foodItemId)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = foodItemController.deleteFoodItemImage(foodItemId);

    assertEquals(expectedResponse, response);
    verify(foodItemService).deleteFoodItemImage(foodItemId);
  }

  @Test
  void getFoodItemsSortedByPrice_Success() {
    boolean ascending = true;
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(foodItemService.getFoodItemsSortedByPrice(ascending)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemController.getFoodItemsSortedByPrice(ascending);

    assertEquals(expectedResponse, response);
    verify(foodItemService).getFoodItemsSortedByPrice(ascending);
  }

  @Test
  void filterByAvailability_Success() {
    boolean availability = true;
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(foodItemService.filterByAvailability(availability)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemController.filterByAvailability(availability);

    assertEquals(expectedResponse, response);
    verify(foodItemService).filterByAvailability(availability);
  }

  @Test
  void filterByIsVeg_Success() {
    boolean isVeg = true;
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(foodItemService.filterByIsVeg(isVeg)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemController.filterByIsVeg(isVeg);

    assertEquals(expectedResponse, response);
    verify(foodItemService).filterByIsVeg(isVeg);
  }

  @Test
  void filterByCategoryName_Success() {
    String categoryName = "Test Category";
    List<FoodItemOutDTO> foodItems = Arrays.asList(new FoodItemOutDTO(), new FoodItemOutDTO());
    ResponseEntity<List<FoodItemOutDTO>> expectedResponse = new ResponseEntity<>(foodItems, HttpStatus.OK);

    when(foodItemService.filterByCategoryName(categoryName)).thenReturn(expectedResponse);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemController.filterByCategoryName(categoryName);

    assertEquals(expectedResponse, response);
    verify(foodItemService).filterByCategoryName(categoryName);
  }
}