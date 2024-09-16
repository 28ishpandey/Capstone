package com.food.restaurant.controller;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.CategoryInDTO;
import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.service.CategoryService;
import com.food.restaurant.util.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CategoryController categoryController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createCategory_Success() {
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Test Category");
    categoryInDTO.setRestaurantId(1L);
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_CREATED_SUCCESSFULLY), HttpStatus.CREATED);

    when(categoryService.createCategory(categoryInDTO)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = categoryController.createCategory(categoryInDTO);

    assertEquals(expectedResponse, response);
    verify(categoryService).createCategory(categoryInDTO);
  }

  @Test
  void getAllCategories_Success() {
    Long restaurantId = 1L;
    List<CategoryOutDTO> categories = Arrays.asList(new CategoryOutDTO(), new CategoryOutDTO());
    ResponseEntity<List<CategoryOutDTO>> expectedResponse = new ResponseEntity<>(categories, HttpStatus.OK);

    when(categoryService.getAllCategoriesForRestaurant(restaurantId)).thenReturn(expectedResponse);

    ResponseEntity<List<CategoryOutDTO>> response = categoryController.getAllCategories(restaurantId);

    assertEquals(expectedResponse, response);
    verify(categoryService).getAllCategoriesForRestaurant(restaurantId);
  }

  @Test
  void updateCategory_Success() {
    Long id = 1L;
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Updated Category");
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_UPDATED_SUCCESSFULLY), HttpStatus.OK);

    when(categoryService.updateCategory(id, categoryInDTO)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = categoryController.updateCategory(id, categoryInDTO);

    assertEquals(expectedResponse, response);
    verify(categoryService).updateCategory(id, categoryInDTO);
  }

  @Test
  void deleteCategory_Success() {
    Long id = 1L;
    ResponseEntity<MessageDTO> expectedResponse =
      new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);

    when(categoryService.deleteCategory(id)).thenReturn(expectedResponse);

    ResponseEntity<MessageDTO> response = categoryController.deleteCategory(id);

    assertEquals(expectedResponse, response);
    verify(categoryService).deleteCategory(id);
  }
}