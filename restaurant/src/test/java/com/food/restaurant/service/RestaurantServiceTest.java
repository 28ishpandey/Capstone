package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.dto.RestaurantDetailsDTO;
import com.food.restaurant.dto.RestaurantImageDTO;
import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantUpdateDTO;
import com.food.restaurant.entity.FoodItem;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.entity.RestaurantOwner;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.repository.CategoryRepository;
import com.food.restaurant.repository.FoodItemRepository;
import com.food.restaurant.repository.RestaurantOwnerRepository;
import com.food.restaurant.repository.RestaurantRepository;
import com.food.restaurant.util.MessageDTO;
import com.food.restaurant.util.PasswordUtil;
import com.food.restaurant.util.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantServiceTest {

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private RestaurantOwnerRepository ownerRepository;

  @Mock
  private RestaurantMapper restaurantMapper;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private FoodItemRepository foodItemRepository;

  @InjectMocks
  private RestaurantService restaurantService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRestaurant_EmailExists() {
    RestaurantInDTO dto = new RestaurantInDTO();
    dto.setEmail("existing@example.com");

    when(restaurantRepository.existsByEmail(anyString())).thenReturn(true);

    ResponseEntity<MessageDTO> response = restaurantService.createRestaurant(dto, null);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(Constants.EMAIL_ALREADY_EXISTS, response.getBody().getMessage());
  }

  @Test
  void createRestaurant_NameExists() {
    RestaurantInDTO dto = new RestaurantInDTO();
    dto.setEmail("new@example.com");
    dto.setName("Existing Restaurant");

    when(restaurantRepository.existsByEmail(anyString())).thenReturn(false);
    when(restaurantRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

    ResponseEntity<MessageDTO> response = restaurantService.createRestaurant(dto, null);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_NAME_ALREADY_EXISTS, response.getBody().getMessage());
  }

  @Test
  void createRestaurant_OwnerNotFound() {
    RestaurantInDTO dto = new RestaurantInDTO();
    dto.setEmail("new@example.com");
    dto.setName("New Restaurant");
    dto.setRestaurantOwnerId(999L);

    when(restaurantRepository.existsByEmail(anyString())).thenReturn(false);
    when(restaurantRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
    when(ownerRepository.existsById(anyLong())).thenReturn(false);

    ResponseEntity<MessageDTO> response = restaurantService.createRestaurant(dto, null);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_OWNER_NOT_FOUND, response.getBody().getMessage());
  }

  @Test
  void updateRestaurant_Success() throws IOException {
    Long id = 1L;
    RestaurantUpdateDTO dto = new RestaurantUpdateDTO();
    dto.setEmail("update@example.com");
    dto.setName("Updated Restaurant");

    Restaurant existingRestaurant = new Restaurant();
    existingRestaurant.setEmail("old@example.com");
    existingRestaurant.setName("Old Restaurant");

    MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());

    when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));
    when(restaurantRepository.existsByEmail(anyString())).thenReturn(false);
    when(restaurantRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);

    ResponseEntity<MessageDTO> response = restaurantService.updateRestaurant(id, dto, image);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_UPDATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(restaurantRepository).save(any(Restaurant.class));
  }

  @Test
  void updateRestaurant_NotFound() {
    Long id = 999L;
    RestaurantUpdateDTO dto = new RestaurantUpdateDTO();

    when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.updateRestaurant(id, dto, null));
  }

  @Test
  void updateRestaurant_EmailExists() {
    Long id = 1L;
    RestaurantUpdateDTO dto = new RestaurantUpdateDTO();
    dto.setEmail("existing@example.com");

    Restaurant existingRestaurant = new Restaurant();
    existingRestaurant.setEmail("old@example.com");

    when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));
    when(restaurantRepository.existsByEmail(anyString())).thenReturn(true);

    ResponseEntity<MessageDTO> response = restaurantService.updateRestaurant(id, dto, null);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(Constants.EMAIL_ALREADY_EXISTS, response.getBody().getMessage());
  }

  @Test
  void updateRestaurant_NameExists() {
    Long id = 1L;
    RestaurantUpdateDTO dto = new RestaurantUpdateDTO();
    dto.setEmail("old@example.com");
    dto.setName("Existing Restaurant");

    Restaurant existingRestaurant = new Restaurant();
    existingRestaurant.setEmail("old@example.com");
    existingRestaurant.setName("Old Restaurant");

    when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));
    when(restaurantRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

    ResponseEntity<MessageDTO> response = restaurantService.updateRestaurant(id, dto, null);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_NAME_ALREADY_EXISTS, response.getBody().getMessage());
  }

  @Test
  void uploadRestaurantImage_Success() throws IOException {
    Long restaurantId = 1L;
    RestaurantImageDTO dto = new RestaurantImageDTO();
    dto.setRestaurantId(restaurantId);
    dto.setImage(new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes()));

    Restaurant restaurant = new Restaurant();
    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    ResponseEntity<String> response = restaurantService.uploadRestaurantImage(dto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.IMAGE_ADDED_SUCCESSFULLY, response.getBody());
    verify(restaurantRepository).save(restaurant);
  }

  @Test
  void uploadRestaurantImage_RestaurantNotFound() {
    Long restaurantId = 999L;
    RestaurantImageDTO dto = new RestaurantImageDTO();
    dto.setRestaurantId(restaurantId);

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.uploadRestaurantImage(dto));
  }

  @Test
  void uploadRestaurantImage_InvalidFormat() {
    Long restaurantId = 1L;
    RestaurantImageDTO dto = new RestaurantImageDTO();
    dto.setRestaurantId(restaurantId);
    dto.setImage(new MockMultipartFile("image", "test.txt", "text/plain", "test".getBytes()));

    Restaurant restaurant = new Restaurant();
    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    assertThrows(IllegalArgumentException.class, () -> restaurantService.uploadRestaurantImage(dto));
  }

  @Test
  void getRestaurantImage_Success() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    byte[] imageData = "test image data".getBytes();
    restaurant.setImage(imageData);

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    ResponseEntity<byte[]> response = restaurantService.getRestaurantImage(restaurantId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertArrayEquals(imageData, response.getBody());
  }

  @Test
  void getRestaurantImage_RestaurantNotFound() {
    Long restaurantId = 999L;

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurantImage(restaurantId));
  }

  @Test
  void getRestaurantImage_NoImage() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    restaurant.setImage(null);

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurantImage(restaurantId));
  }

  @Test
  void updateRestaurantImage_Success() throws IOException {
    Long restaurantId = 1L;
    RestaurantImageDTO dto = new RestaurantImageDTO();
    dto.setRestaurantId(restaurantId);
    dto.setImage(new MockMultipartFile("image", "test.jpg", "image/jpeg", "updated image".getBytes()));

    Restaurant restaurant = new Restaurant();
    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    ResponseEntity<MessageDTO> response = restaurantService.updateRestaurantImage(dto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.IMAGE_UPDATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(restaurantRepository).save(restaurant);
  }

  @Test
  void deleteRestaurantImage_Success() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    restaurant.setImage("existing image".getBytes());

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    ResponseEntity<String> response = restaurantService.deleteRestaurantImage(restaurantId);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(Constants.IMAGE_DELETED_SUCCESSFULLY, response.getBody());
    assertNull(restaurant.getImage());
    verify(restaurantRepository).save(restaurant);
  }

  @Test
  void deleteRestaurantImage_NoImage() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    restaurant.setImage(null);

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.deleteRestaurantImage(restaurantId));
  }

  @Test
  void deleteRestaurant_Success() {
    Long restaurantId = 1L;
    String ownerPassword = "correctPassword";
    Restaurant restaurant = new Restaurant();
    restaurant.setRestaurantOwnerId(1L);
    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode(ownerPassword));

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
    when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

    ResponseEntity<MessageDTO> response = restaurantService.deleteRestaurant(restaurantId, ownerPassword);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_DELETED_SUCCESSFULLY, response.getBody().getMessage());
    verify(restaurantRepository).deleteById(restaurantId);
  }

  @Test
  void deleteRestaurant_RestaurantNotFound() {
    Long restaurantId = 999L;
    String ownerPassword = "password";

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

    ResponseEntity<MessageDTO> response = restaurantService.deleteRestaurant(restaurantId, ownerPassword);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(Constants.RESOURCE_NOT_FOUND, response.getBody().getMessage());
  }

  @Test
  void deleteRestaurant_OwnerNotFound() {
    Long restaurantId = 1L;
    String ownerPassword = "password";
    Restaurant restaurant = new Restaurant();
    restaurant.setRestaurantOwnerId(999L);

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
    when(ownerRepository.findById(999L)).thenReturn(Optional.empty());

    ResponseEntity<MessageDTO> response = restaurantService.deleteRestaurant(restaurantId, ownerPassword);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(Constants.RESOURCE_NOT_FOUND, response.getBody().getMessage());
  }

  @Test
  void deleteRestaurant_InvalidPassword() {
    Long restaurantId = 1L;
    String ownerPassword = "wrongPassword";
    Restaurant restaurant = new Restaurant();
    restaurant.setRestaurantOwnerId(1L);
    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode("correctPassword"));

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
    when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

    ResponseEntity<MessageDTO> response = restaurantService.deleteRestaurant(restaurantId, ownerPassword);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid owner password.", response.getBody().getMessage());
  }

  @Test
  void getRestaurant_Success() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
    when(restaurantMapper.toDto(restaurant)).thenReturn(restaurantOutDTO);

    ResponseEntity<?> response = restaurantService.getRestaurant(restaurantId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(restaurantOutDTO, response.getBody());
  }

  @Test
  void getRestaurant_NotFound() {
    Long restaurantId = 999L;

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

    ResponseEntity<?> response = restaurantService.getRestaurant(restaurantId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Restaurant Not Found", ((MessageDTO) response.getBody()).getMessage());
  }

  @Test
  void getAllRestaurants_Success() {
    List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
    List<RestaurantOutDTO> restaurantOutDTOs = Arrays.asList(new RestaurantOutDTO(), new RestaurantOutDTO());

    when(restaurantRepository.findAll()).thenReturn(restaurants);
    when(restaurantMapper.toDto(any(Restaurant.class))).thenReturn(new RestaurantOutDTO());

    ResponseEntity<List<RestaurantOutDTO>> response = restaurantService.getAllRestaurants();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void setRestaurantStatus_Success() {
    Long restaurantId = 1L;
    Boolean isOpen = true;
    Restaurant restaurant = new Restaurant();

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

    ResponseEntity<MessageDTO> response = restaurantService.setRestaurantStatus(restaurantId, isOpen);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Restaurant status set to open successfully", response.getBody().getMessage());
    assertTrue(restaurant.getIsOpen());
    verify(restaurantRepository).save(restaurant);
  }

  @Test
  void setRestaurantStatus_RestaurantNotFound() {
    Long restaurantId = 999L;
    Boolean isOpen = true;

    when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.setRestaurantStatus(restaurantId, isOpen));
  }

  @Test
  void getAllFoodItemsByRestaurant_NoCategories() {
    Long restaurantId = 1L;

    when(categoryRepository.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.getAllFoodItemsByRestaurant(restaurantId));
  }

  @Test
  void getAllFoodItemsOfRestaurant_Success() {
    Long restaurantId = 1L;
    List<FoodItem> foodItems = Arrays.asList(new FoodItem(), new FoodItem());

    when(foodItemRepository.findByRestaurantId(restaurantId)).thenReturn(foodItems);

    ResponseEntity<List<FoodItemOutDTO>> response = restaurantService.getAllFoodItemsOfRestaurant(restaurantId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void getAllFoodItemsOfRestaurant_NoItems() {
    Long restaurantId = 1L;

    when(foodItemRepository.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.getAllFoodItemsOfRestaurant(restaurantId));
  }

  @Test
  void getAllRestaurantsWithDetails_NoRestaurants() {
    when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<RestaurantDetailsDTO>> response = restaurantService.getAllRestaurantsWithDetails();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
  }
}