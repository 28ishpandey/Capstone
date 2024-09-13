package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.FoodItemImageDTO;
import com.food.restaurant.dto.FoodItemInDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.entity.Category;
import com.food.restaurant.entity.FoodItem;
import com.food.restaurant.exception.InvalidInputException;
import com.food.restaurant.exception.ResourceAlreadyExistException;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.repository.CategoryRepository;
import com.food.restaurant.repository.FoodItemRepository;
import com.food.restaurant.repository.RestaurantRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FoodItemServiceTest {

  @Mock
  private FoodItemRepository foodItemRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private RestaurantRepository restaurantRepository;

  @InjectMocks
  private FoodItemService foodItemService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createFoodItem_Success() {
    FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
    foodItemInDTO.setName("Test Food Item");
    foodItemInDTO.setRestaurantId(1L);
    MultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());

    when(restaurantRepository.existsById(foodItemInDTO.getRestaurantId())).thenReturn(true);
    when(foodItemRepository.existsByRestaurantIdAndNameIgnoreCase(foodItemInDTO.getRestaurantId(),
      foodItemInDTO.getName().trim().toLowerCase())).thenReturn(false);

    ResponseEntity<MessageDTO> response = foodItemService.createFoodItem(foodItemInDTO, image);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(Constants.FOOD_ITEM_CREATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(foodItemRepository).save(any(FoodItem.class));
  }

  @Test
  void createFoodItem_RestaurantNotFound() {
    FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
    foodItemInDTO.setRestaurantId(999L);

    when(restaurantRepository.existsById(foodItemInDTO.getRestaurantId())).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.createFoodItem(foodItemInDTO, null));
    verify(foodItemRepository, never()).save(any(FoodItem.class));
  }

  @Test
  void createFoodItem_NameExists() {
    FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
    foodItemInDTO.setName("Existing Food Item");
    foodItemInDTO.setRestaurantId(1L);

    when(restaurantRepository.existsById(foodItemInDTO.getRestaurantId())).thenReturn(true);
    when(foodItemRepository.existsByRestaurantIdAndNameIgnoreCase(foodItemInDTO.getRestaurantId(),
      foodItemInDTO.getName().trim().toLowerCase())).thenReturn(true);

    assertThrows(ResourceAlreadyExistException.class, () -> foodItemService.createFoodItem(foodItemInDTO, null));
    verify(foodItemRepository, never()).save(any(FoodItem.class));
  }

  @Test
  void getAllFoodItemsForCategory_Success() {
    Long categoryId = 1L;
    List<FoodItem> foodItems = Arrays.asList(new FoodItem(), new FoodItem());

    when(foodItemRepository.findByCategoryId(categoryId)).thenReturn(foodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.getAllFoodItemsForCategory(categoryId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }


  @Test
  void deleteFoodItem_Success() {
    Long id = 1L;
    FoodItem foodItem = new FoodItem();
    foodItem.setId(id);

    when(foodItemRepository.findById(id)).thenReturn(Optional.of(foodItem));

    ResponseEntity<MessageDTO> response = foodItemService.deleteFoodItem(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(Constants.FOOD_ITEM_DELETED_SUCCESSFULLY, response.getBody().getMessage());
    verify(foodItemRepository).delete(foodItem);
  }

  @Test
  void deleteFoodItem_NotFound() {
    Long id = 999L;

    when(foodItemRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.deleteFoodItem(id));
    verify(foodItemRepository, never()).delete(any(FoodItem.class));
  }

  @Test
  void uploadFoodItemImage_Success() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    foodItemImageDTO.setFoodItemId(1L);
    foodItemImageDTO.setImage(new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes()));
    FoodItem foodItem = new FoodItem();

    when(foodItemRepository.findById(foodItemImageDTO.getFoodItemId())).thenReturn(Optional.of(foodItem));

    ResponseEntity<String> response = foodItemService.uploadFoodItemImage(foodItemImageDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.IMAGE_ADDED_SUCCESSFULLY, response.getBody());
    verify(foodItemRepository).save(foodItem);
  }

  @Test
  void uploadFoodItemImage_FoodItemNotFound() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    foodItemImageDTO.setFoodItemId(999L);

    when(foodItemRepository.findById(foodItemImageDTO.getFoodItemId())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.uploadFoodItemImage(foodItemImageDTO));
    verify(foodItemRepository, never()).save(any(FoodItem.class));
  }

  @Test
  void getFoodItemImage_Success() {
    Long foodItemId = 1L;
    FoodItem foodItem = new FoodItem();
    foodItem.setImage("test image content".getBytes());

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.of(foodItem));

    ResponseEntity<byte[]> response = foodItemService.getFoodItemImage(foodItemId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertArrayEquals(foodItem.getImage(), response.getBody());
  }

  @Test
  void getFoodItemImage_FoodItemNotFound() {
    Long foodItemId = 999L;

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.getFoodItemImage(foodItemId));
  }

  @Test
  void updateFoodItemImage_Success() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    foodItemImageDTO.setFoodItemId(1L);
    foodItemImageDTO.setImage(new MockMultipartFile("image", "test.jpg", "image/jpeg", "updated image content".getBytes()));
    FoodItem foodItem = new FoodItem();

    when(foodItemRepository.findById(foodItemImageDTO.getFoodItemId())).thenReturn(Optional.of(foodItem));

    ResponseEntity<MessageDTO> response = foodItemService.updateFoodItemImage(foodItemImageDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.IMAGE_UPDATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(foodItemRepository).save(foodItem);
  }

  @Test
  void updateFoodItemImage_FoodItemNotFound() {
    FoodItemImageDTO foodItemImageDTO = new FoodItemImageDTO();
    foodItemImageDTO.setFoodItemId(999L);

    when(foodItemRepository.findById(foodItemImageDTO.getFoodItemId())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.updateFoodItemImage(foodItemImageDTO));
    verify(foodItemRepository, never()).save(any(FoodItem.class));
  }

  @Test
  void deleteFoodItemImage_Success() {
    Long foodItemId = 1L;
    FoodItem foodItem = new FoodItem();
    foodItem.setImage("test image content".getBytes());

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.of(foodItem));

    ResponseEntity<MessageDTO> response = foodItemService.deleteFoodItemImage(foodItemId);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(Constants.IMAGE_DELETED_SUCCESSFULLY, response.getBody().getMessage());
    assertNull(foodItem.getImage());
    verify(foodItemRepository).save(foodItem);
  }

  @Test
  void deleteFoodItemImage_FoodItemNotFound() {
    Long foodItemId = 999L;

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.deleteFoodItemImage(foodItemId));
    verify(foodItemRepository, never()).save(any(FoodItem.class));
  }

  @Test
  void getFoodItemsSortedByPrice_AscendingOrder() {
    List<FoodItem> sortedFoodItems = Arrays.asList(new FoodItem(), new FoodItem());
    when(foodItemRepository.findAllByOrderByPriceAsc()).thenReturn(sortedFoodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.getFoodItemsSortedByPrice(true);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(foodItemRepository).findAllByOrderByPriceAsc();
  }

  @Test
  void getFoodItemsSortedByPrice_DescendingOrder() {
    List<FoodItem> sortedFoodItems = Arrays.asList(new FoodItem(), new FoodItem());
    when(foodItemRepository.findAllByOrderByPriceDesc()).thenReturn(sortedFoodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.getFoodItemsSortedByPrice(false);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(foodItemRepository).findAllByOrderByPriceDesc();
  }

  @Test
  void filterByAvailability_Success() {
    boolean availability = true;
    List<FoodItem> foodItems = Arrays.asList(new FoodItem(), new FoodItem());
    when(foodItemRepository.findByAvailability(availability)).thenReturn(foodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.filterByAvailability(availability);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(foodItemRepository).findByAvailability(availability);
  }

  @Test
  void filterByIsVeg_Success() {
    boolean isVeg = true;
    List<FoodItem> foodItems = Arrays.asList(new FoodItem(), new FoodItem());
    when(foodItemRepository.findByIsVeg(isVeg)).thenReturn(foodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.filterByIsVeg(isVeg);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(foodItemRepository).findByIsVeg(isVeg);
  }

  @Test
  void filterByCategoryName_Success() {
    String categoryName = "Test Category";
    List<Category> categories = Arrays.asList(new Category(), new Category());
    List<FoodItem> foodItems = Arrays.asList(new FoodItem(), new FoodItem());

    when(categoryRepository.findByNameIgnoreCase(categoryName)).thenReturn(categories);
    when(foodItemRepository.findByCategoryIdIn(anyList())).thenReturn(foodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = foodItemService.filterByCategoryName(categoryName);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(categoryRepository).findByNameIgnoreCase(categoryName);
    verify(foodItemRepository).findByCategoryIdIn(anyList());
  }

  @Test
  void filterByCategoryName_CategoryNotFound() {
    String categoryName = "Non-existent Category";
    when(categoryRepository.findByNameIgnoreCase(categoryName)).thenReturn(Arrays.asList());

    assertThrows(ResourceNotFoundException.class, () -> foodItemService.filterByCategoryName(categoryName));
    verify(foodItemRepository, never()).findByCategoryIdIn(anyList());
  }

  @Test
  void validateImageFormat_ValidFormat() {
    MultipartFile validImage = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
    assertDoesNotThrow(() -> foodItemService.validateImageFormat(validImage));
  }

  @Test
  void validateImageFormat_InvalidFormat() {
    MultipartFile invalidImage = new MockMultipartFile("image", "test.txt", "text/plain", "test content".getBytes());
    assertThrows(InvalidInputException.class, () -> foodItemService.validateImageFormat(invalidImage));
  }
}