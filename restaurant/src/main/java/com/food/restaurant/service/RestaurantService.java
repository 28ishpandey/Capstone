package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.CategoryDetailsDTO;
import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.dto.FoodItemDetailsDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.dto.RestaurantDetailsDTO;
import com.food.restaurant.dto.RestaurantImageDTO;
import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantUpdateDTO;
import com.food.restaurant.entity.Category;
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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service class for managing restaurant-related operations, including CRUD operations for restaurants,
 * uploading and updating images, and managing food items and categories.
 */
@Service
@Slf4j
public class RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private RestaurantOwnerRepository ownerRepository;

  @Autowired
  private RestaurantMapper restaurantMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;
  /**
   * Creates a new restaurant with the given details and image.
   *
   * @param restaurantDto the details of the restaurant to be created
   * @param image the image file associated with the restaurant
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<MessageDTO> createRestaurant(RestaurantInDTO restaurantDto, MultipartFile image) {
    log.info("Entering createRestaurantWithImage with email: {}", restaurantDto.getEmail());

    String trimmedEmail = restaurantDto.getEmail().trim().toLowerCase();
    if (restaurantRepository.existsByEmail(trimmedEmail)) {
      log.warn("Restaurant email already exists: {}", restaurantDto.getEmail());
      return new ResponseEntity<>(new MessageDTO(Constants.EMAIL_ALREADY_EXISTS), HttpStatus.CONFLICT);
    }

    String normalizedName = restaurantDto.getName().trim().toLowerCase();
    if (restaurantRepository.existsByNameIgnoreCase(normalizedName)) {
      log.warn("Restaurant name already exists: {}", restaurantDto.getName());
      return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_NAME_ALREADY_EXISTS), HttpStatus.CONFLICT);
    }

    if (!ownerRepository.existsById(restaurantDto.getRestaurantOwnerId())) {
      log.warn("Restaurant owner not found for ID: {}", restaurantDto.getRestaurantOwnerId());
      return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_OWNER_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
    restaurant.setEmail(trimmedEmail);
    restaurant.setName(restaurantDto.getName().trim());
    restaurant.setPassword(PasswordUtil.encode(restaurantDto.getPassword()));

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFormat(image);
        restaurant.setImage(image.getBytes());
      } catch (IOException e) {
        log.error("Error processing image for restaurant creation", e);
        throw new RuntimeException("Error processing image", e);
      }
    }

    restaurantRepository.save(restaurant);
    log.info("Successfully created restaurant with email: {} and name: {}", restaurant.getEmail(), restaurant.getName());

    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
  }
  /**
   * Updates an existing restaurant with the given ID, details, and image.
   *
   * @param id the ID of the restaurant to be updated
   * @param restaurantDto the new details for the restaurant
   * @param image the new image file for the restaurant
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<MessageDTO> updateRestaurant(Long id, RestaurantUpdateDTO restaurantDto, MultipartFile image) {
    log.info("Entering updateRestaurant with id: {} and email: {}", id, restaurantDto.getEmail());

    Restaurant restaurant = restaurantRepository.findById(id)
      .orElseThrow(() -> {
        log.warn("Restaurant not found with id: {}", id);
        return new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
      });
    if (!restaurant.getEmail().equals(restaurantDto.getEmail())) {
      String trimmedEmail = restaurantDto.getEmail().trim().toLowerCase();
      if (restaurantRepository.existsByEmail(trimmedEmail)) {
        log.warn("Email already in use: {}", trimmedEmail);
        return new ResponseEntity<>(new MessageDTO(Constants.EMAIL_ALREADY_EXISTS), HttpStatus.CONFLICT);
      }
      restaurant.setEmail(trimmedEmail);
    }
    if (!restaurant.getName().equals(restaurantDto.getName())) {
      String normalizedName = restaurantDto.getName().trim().toLowerCase();
      if (restaurantRepository.existsByNameIgnoreCase(normalizedName)) {
        log.warn("Restaurant name already exists: {}", restaurantDto.getName());
        return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_NAME_ALREADY_EXISTS), HttpStatus.CONFLICT);
      }
      restaurant.setName(restaurantDto.getName().trim());
    }
    restaurant.setContactNumber(restaurantDto.getContactNumber());
    restaurant.setAddress(restaurantDto.getAddress());
    restaurant.setTimings(restaurantDto.getTimings());
    restaurant.setIsOpen(restaurantDto.getIsOpen());

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFormat(image);
        restaurant.setImage(image.getBytes());
      } catch (IOException e) {
        log.error("Error processing image for restaurant update", e);
        throw new RuntimeException("Error processing image", e);
      }
    }

    restaurantRepository.save(restaurant);

    log.info("Successfully updated restaurant with id: {}", id);
    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }
  /**
   * Uploads an image for a restaurant.
   *
   * @param restaurantImageDTO contains the restaurant ID and image file
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<String> uploadRestaurantImage(RestaurantImageDTO restaurantImageDTO) {
    Restaurant restaurant = findRestaurantById(restaurantImageDTO.getRestaurantId());

    validateImageFormat(restaurantImageDTO.getImage());

    try {
      restaurant.setImage(restaurantImageDTO.getImage().getBytes());
      restaurantRepository.save(restaurant);
      log.info("Image uploaded for restaurant with ID: {}", restaurant.getId());
    } catch (IOException e) {
      log.error("Error uploading image for restaurant with ID: {}", restaurant.getId(), e);
      throw new RuntimeException("Error uploading image", e);
    }

    return new ResponseEntity<>(Constants.IMAGE_ADDED_SUCCESSFULLY, HttpStatus.OK);
  }

  /**
   * Retrieves the image for a restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a ResponseEntity containing the image bytes and HTTP headers
   */
  public ResponseEntity<byte[]> getRestaurantImage(Long restaurantId) {
    Restaurant restaurant = findRestaurantById(restaurantId);

    byte[] image = restaurant.getImage();
    if (image == null) {
      log.warn("No image found for restaurant with ID: {}", restaurantId);
      throw new ResourceNotFoundException("No image found for restaurant ID: " + restaurantId);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG); // You might want to check the actual content type

    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }
  /**
   * Updates the image for a restaurant.
   *
   * @param restaurantImageDTO contains the restaurant ID and new image file
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<MessageDTO> updateRestaurantImage(RestaurantImageDTO restaurantImageDTO) {
    Restaurant restaurant = findRestaurantById(restaurantImageDTO.getRestaurantId());

    validateImageFormat(restaurantImageDTO.getImage());

    try {
      restaurant.setImage(restaurantImageDTO.getImage().getBytes());
      restaurantRepository.save(restaurant);
      log.info("Image updated for restaurant with ID: {}", restaurant.getId());
    } catch (IOException e) {
      log.error("Error updating image for restaurant with ID: {}", restaurant.getId(), e);
      throw new RuntimeException("Error updating image", e);
    }

    return new ResponseEntity<>(new MessageDTO(Constants.IMAGE_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }
  /**
   * Deletes the image for a restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<String> deleteRestaurantImage(Long restaurantId) {
    Restaurant restaurant = findRestaurantById(restaurantId);

    if (restaurant.getImage() == null) {
      log.warn("No image to delete for restaurant with ID: {}", restaurantId);
      throw new ResourceNotFoundException("No image to delete for restaurant ID: " + restaurantId);
    }

    restaurant.setImage(null);
    restaurantRepository.save(restaurant);
    log.info("Image deleted for restaurant with ID: {}", restaurantId);

    return new ResponseEntity<>(Constants.IMAGE_DELETED_SUCCESSFULLY, HttpStatus.NO_CONTENT);
  }
  /**
   * Finds a restaurant by its ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return the Restaurant entity
   * @throws ResourceNotFoundException if the restaurant is not found
   */
  private Restaurant findRestaurantById(Long restaurantId) {
    return restaurantRepository.findById(restaurantId)
      .orElseThrow(() -> {
        log.warn("Restaurant not found with ID: {}", restaurantId);
        return new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
      });
  }
  /**
   * Validates the format of the given image file.
   *
   * @param file the image file to be validated
   * @throws IllegalArgumentException if the file format is not allowed
   */
  private void validateImageFormat(MultipartFile file) {
    String contentType = file.getContentType();
    if (!"image/jpeg".equals(contentType) && !"image/jpg".equals(contentType) && !"image/png".equals(contentType)) {
      log.warn("Invalid image format: {}", contentType);
      throw new IllegalArgumentException("Invalid file format. Only JPEG, JPG, and PNG are allowed.");
    }
  }
  /**
   * Deletes a restaurant with the given ID after verifying the owner's password.
   *
   * @param id the ID of the restaurant to be deleted
   * @param ownerPassword the password of the restaurant owner
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  public ResponseEntity<MessageDTO> deleteRestaurant(Long id, String ownerPassword) {
    log.info("Entering deleteRestaurant with id: {}", id);

    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
    if (restaurant.isEmpty()) {
      log.warn("Restaurant not found with id: {}", id);
      return new ResponseEntity<>(new MessageDTO(Constants.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }
    Long ownerId = restaurant.get().getRestaurantOwnerId();
    Optional<RestaurantOwner> owner = ownerRepository.findById(ownerId);

    if (owner.isEmpty()) {
      log.warn("Owner not found with id: {}", ownerId);
      return new ResponseEntity<>(new MessageDTO(Constants.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    String decodedPassword = PasswordUtil.decode(owner.get().getPassword());
    if (!decodedPassword.equals(ownerPassword)) {
      log.warn("Invalid password for deleting restaurant with id: {}", id);
      return new ResponseEntity<>(new MessageDTO("Invalid owner password."), HttpStatus.UNAUTHORIZED);
    }
    restaurantRepository.deleteById(id);
    log.info("Exiting deleteRestaurant with id: {}", id);
    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_DELETED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Retrieves the details of a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return a ResponseEntity containing the restaurant details
   */
  public ResponseEntity<?> getRestaurant(Long id) {
    log.info("Entering getRestaurant with id: {}", id);

    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
    if (restaurant.isEmpty()) {
      log.warn("Restaurant not found with id: {}", id);
      return new ResponseEntity<>(new MessageDTO("Restaurant Not Found"), HttpStatus.NOT_FOUND);
    }

    RestaurantOutDTO restaurantDto = restaurantMapper.toDto(restaurant.get());
    log.info("Exiting getRestaurant with id: {}", id);
    return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
  }
  /**
   * Retrieves a list of all restaurants.
   *
   * @return a ResponseEntity containing a list of RestaurantOutDTO
   */
  public ResponseEntity<List<RestaurantOutDTO>> getAllRestaurants() {
    log.info("Entering getAllRestaurants");

    List<Restaurant> restaurants = restaurantRepository.findAll();
    List<RestaurantOutDTO> restaurantDtos = restaurants.stream()
      .map(restaurantMapper::toDto)
      .toList();

    log.info("Exiting getAllRestaurants");
    return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
  }
  /**
   * Sets the operational status of a restaurant.
   *
   * @param id the ID of the restaurant
   * @param isOpen the new status of the restaurant
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  @Transactional
  public ResponseEntity<MessageDTO> setRestaurantStatus(Long id, Boolean isOpen) {
    log.info("Entering setRestaurantStatus with id: {} and isOpen: {}", id, isOpen);

    Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
    if (restaurantOpt.isEmpty()) {
      log.warn("Restaurant not found with id: {}", id);
      throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
    }

    Restaurant restaurant = restaurantOpt.get();
    restaurant.setIsOpen(isOpen);
    restaurantRepository.save(restaurant);

    String status = isOpen ? "open" : "closed";
    log.info("Successfully set restaurant status to {} for id: {}", status, id);
    return new ResponseEntity<>(new MessageDTO("Restaurant status set to " + status + " successfully"), HttpStatus.OK);
  }
  /**
   * Retrieves all food items categorized by restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a ResponseEntity containing a list of CategoryOutDTO with nested food items
   */
  @Transactional
  public ResponseEntity<List<CategoryOutDTO>> getAllFoodItemsByRestaurant(Long restaurantId) {
    log.info("Fetching all categories and their food items for restaurant ID: {}", restaurantId);

    List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
    if (categories.isEmpty()) {
      throw new ResourceNotFoundException(Constants.NO_CATEGORIES_FOUND_FOR_RESTAURANT);
    }
    List<CategoryOutDTO> categoryOutDTOs = categories.stream().map(category -> {
      CategoryOutDTO categoryOutDTO = new CategoryOutDTO();
      categoryOutDTO.setId(category.getId());
      categoryOutDTO.setRestaurantId(category.getRestaurantId());
      categoryOutDTO.setName(category.getName());

      List<FoodItem> foodItems = foodItemRepository.findByCategoryId(category.getId());
      List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream().map(foodItem -> {
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setId(foodItem.getId());
        foodItemOutDTO.setCategoryId(foodItem.getCategoryId());
        foodItemOutDTO.setRestaurantId(foodItem.getRestaurantId());
        foodItemOutDTO.setName(foodItem.getName());
        foodItemOutDTO.setPrice(foodItem.getPrice());
        foodItemOutDTO.setAvailability(foodItem.isAvailability());
        foodItemOutDTO.setVeg(foodItem.isVeg());
        foodItemOutDTO.setDescription(foodItem.getDescription());
        foodItemOutDTO.setImage(foodItem.getImage());
        return foodItemOutDTO;
      }).collect(Collectors.toList());

      categoryOutDTO.setFoodItems(foodItemOutDTOs);
      return categoryOutDTO;
    }).collect(Collectors.toList());

    log.info("Successfully fetched categories and food items for restaurant ID: {}", restaurantId);
    return new ResponseEntity<>(categoryOutDTOs, HttpStatus.OK);
  }
  /**
   * Retrieves all food items for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a ResponseEntity containing a list of FoodItemOutDTO
   */
  @Transactional
  public ResponseEntity<List<FoodItemOutDTO>> getAllFoodItemsOfRestaurant(Long restaurantId) {
    log.info("Fetching all food items for restaurant ID: {}", restaurantId);

    List<FoodItem> foodItems = foodItemRepository.findByRestaurantId(restaurantId);
    if (foodItems.isEmpty()) {
      throw new ResourceNotFoundException(Constants.NO_FOOD_ITEMS_FOUND_FOR_RESTAURANT);
    }
    List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream().map(foodItem -> {
      FoodItemOutDTO dto = new FoodItemOutDTO();
      dto.setId(foodItem.getId());
      dto.setCategoryId(foodItem.getCategoryId());
      dto.setRestaurantId(foodItem.getRestaurantId());
      dto.setName(foodItem.getName());
      dto.setPrice(foodItem.getPrice());
      dto.setAvailability(foodItem.isAvailability());
      dto.setVeg(foodItem.isVeg());
      dto.setDescription(foodItem.getDescription());
      dto.setImage(foodItem.getImage());
      return dto;
    }).collect(Collectors.toList());

    log.info("Successfully fetched all food items for restaurant ID: {}", restaurantId);
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }
  /**
   * Retrieves all restaurants with their detailed categories and food items.
   *
   * @return a ResponseEntity containing a list of RestaurantDetailsDTO
   */
  @Transactional
  public ResponseEntity<List<RestaurantDetailsDTO>> getAllRestaurantsWithDetails() {
    log.info("Fetching all restaurants with their categories and food items");

    List<Restaurant> restaurants = restaurantRepository.findAll();

    List<RestaurantDetailsDTO> restaurantDetailsDTOs = restaurants.stream()
      .map(restaurant -> {
        RestaurantDetailsDTO restaurantDetailsDTO = new RestaurantDetailsDTO();
        restaurantDetailsDTO.setRestaurantId(restaurant.getId());
        restaurantDetailsDTO.setRestaurantName(restaurant.getName());
        restaurantDetailsDTO.setEmail(restaurant.getEmail());
        restaurantDetailsDTO.setContactNumber(restaurant.getContactNumber());
        restaurantDetailsDTO.setAddress(restaurant.getAddress());
        restaurantDetailsDTO.setTimings(restaurant.getTimings());
        restaurantDetailsDTO.setIsOpen(restaurant.getIsOpen());
        restaurantDetailsDTO.setImage(restaurant.getImage());

        List<Category> categories = categoryRepository.findByRestaurantId(restaurant.getId());
        List<CategoryDetailsDTO> categoryDetailsDTOs = categories.stream()
          .map(category -> {
            CategoryDetailsDTO categoryDetailsDTO = new CategoryDetailsDTO();
            categoryDetailsDTO.setCategoryId(category.getId());
            categoryDetailsDTO.setCategoryName(category.getName());

            List<FoodItem> foodItems = foodItemRepository.findByCategoryId(category.getId());
            List<FoodItemDetailsDTO> foodItemDetailsDTOs = foodItems.stream()
              .map(foodItem -> {
                FoodItemDetailsDTO foodItemDetailsDTO = new FoodItemDetailsDTO();
                foodItemDetailsDTO.setFoodItemId(foodItem.getId());
                foodItemDetailsDTO.setFoodItemName(foodItem.getName());
                foodItemDetailsDTO.setPrice(foodItem.getPrice());
                foodItemDetailsDTO.setAvailability(foodItem.isAvailability());
                foodItemDetailsDTO.setVeg(foodItem.isVeg());
                foodItemDetailsDTO.setDescription(foodItem.getDescription());
                foodItemDetailsDTO.setImage(foodItem.getImage());
                return foodItemDetailsDTO;
              }).collect(Collectors.toList());

            categoryDetailsDTO.setFoodItems(foodItemDetailsDTOs);
            return categoryDetailsDTO;
          }).collect(Collectors.toList());

        restaurantDetailsDTO.setCategories(categoryDetailsDTOs);
        return restaurantDetailsDTO;
      }).collect(Collectors.toList());

    log.info("Fetched {} restaurants with details", restaurantDetailsDTOs.size());
    return new ResponseEntity<>(restaurantDetailsDTOs, HttpStatus.OK);
  }
}
