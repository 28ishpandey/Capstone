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
import java.util.stream.Collectors;

/**
 * Service class for managing food items in the restaurant application.
 * Provides methods to create, update, delete, and retrieve food items, as well as manage their images.
 * Also supports sorting and filtering food items based on various criteria.
 * <p>
 * This class uses {@link FoodItemRepository}, {@link CategoryRepository}, and {@link RestaurantRepository}
 * to interact with the database. It handles exceptions related to invalid input, resource existence, and image processing.
 * </p>
 */
@Service
@Slf4j
public class FoodItemService {

  @Autowired
  private FoodItemRepository foodItemRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  /**
   * Creates a new food item and saves it to the database.
   * <p>
   * Validates the restaurant ID and ensures the food item name does not already exist for the specified restaurant.
   * If an image is provided, it is validated and stored as a byte array.
   * </p>
   *
   * @param foodItemInDTO the DTO containing information about the food item to be created
   * @param image         the image of the food item, which can be null
   * @return a {@link ResponseEntity} with a {@link MessageDTO} indicating success or failure
   */
  @Transactional
  public ResponseEntity<MessageDTO> createFoodItem(FoodItemInDTO foodItemInDTO, MultipartFile image) {
    if (!restaurantRepository.existsById(foodItemInDTO.getRestaurantId())) {
      log.warn("Restaurant not found with ID: {}", foodItemInDTO.getRestaurantId());
      throw new ResourceNotFoundException("Restaurant not found with ID: " + foodItemInDTO.getRestaurantId());
    }

    String foodItemName = foodItemInDTO.getName().trim().toLowerCase();


    boolean exists = foodItemRepository.existsByRestaurantIdAndNameIgnoreCase(foodItemInDTO.getRestaurantId(), foodItemName);
    if (exists) {
      log.warn("Food item name '{}' already exists for restaurant ID: {}", foodItemInDTO.getName(), foodItemInDTO.getRestaurantId());
      throw new ResourceAlreadyExistException("Food item name already exists for the specified restaurant.");
    }

    FoodItem foodItem = new FoodItem();
    foodItem.setCategoryId(foodItemInDTO.getCategoryId());
    foodItem.setRestaurantId(foodItemInDTO.getRestaurantId());
    foodItem.setName(foodItemName);
    foodItem.setPrice(foodItemInDTO.getPrice());
    foodItem.setAvailability(foodItemInDTO.isAvailability());
    foodItem.setVeg(foodItemInDTO.isVeg());
    foodItem.setDescription(foodItemInDTO.getDescription());
    if (image != null && !image.isEmpty()) {
      try {
        validateImageFormat(image);
        foodItem.setImage(image.getBytes());
      } catch (IOException e) {
        log.error("Error processing image for food item creation", e);
        throw new RuntimeException("Error processing image", e);
      }
    }
    foodItemRepository.save(foodItem);
    log.info("Food item '{}' created successfully for restaurant ID: {}", foodItem.getName(), foodItem.getRestaurantId());
    return new ResponseEntity<>(new MessageDTO(Constants.FOOD_ITEM_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
  }

  /**
   * Retrieves all food items for a specific category.
   *
   * @param categoryId the ID of the category
   * @return a {@link ResponseEntity} containing a list of {@link FoodItemOutDTO} for the specified category
   */

  public ResponseEntity<List<FoodItemOutDTO>> getAllFoodItemsForCategory(Long categoryId) {
    List<FoodItem> foodItems = foodItemRepository.findByCategoryId(categoryId);
    List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Retrieved {} food items for category ID: {}", foodItems.size(), categoryId);
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }

  /**
   * Updates an existing food item with new information.
   * <p>
   * Validates the existence of the food item and ensures that the updated name does not already exist for the
   * specified restaurant. Updates the image if provided.
   * </p>
   *
   * @param id            the ID of the food item to be updated
   * @param foodItemInDTO the DTO containing updated information about the food item
   * @param image         the new image of the food item, which can be null
   * @return a {@link ResponseEntity} with a {@link MessageDTO} indicating success or failure
   */
  public ResponseEntity<MessageDTO> updateFoodItem(Long id, FoodItemInDTO foodItemInDTO, MultipartFile image) {
    FoodItem foodItem = foodItemRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + id));

    String foodItemName = foodItemInDTO.getName().trim().toLowerCase();
    boolean exists = foodItemRepository.existsByRestaurantIdAndNameIgnoreCase(foodItemInDTO.getRestaurantId(), foodItemName);
    if (exists && !foodItemName.equalsIgnoreCase(foodItem.getName())) {
      log.warn("Food item name '{}' already exists for restaurant ID: {}", foodItemInDTO.getName(), foodItemInDTO.getRestaurantId());
      throw new ResourceAlreadyExistException("Food item name already exists for the specified restaurant.");
    }

    foodItem.setCategoryId(foodItemInDTO.getCategoryId());
    foodItem.setRestaurantId(foodItemInDTO.getRestaurantId());
    foodItem.setName(foodItemName);
    foodItem.setPrice(foodItemInDTO.getPrice());
    foodItem.setAvailability(foodItemInDTO.isAvailability());
    foodItem.setVeg(foodItemInDTO.isVeg());
    foodItem.setDescription(foodItemInDTO.getDescription());

    if (image != null && !image.isEmpty()) {
      try {
        foodItem.setImage(image.getBytes());
      } catch (IOException e) {
        log.error("Error processing image for food item update", e);
        throw new RuntimeException("Error processing image", e);
      }
    }

    foodItemRepository.save(foodItem);
    log.info("Food item '{}' updated successfully for restaurant ID: {}", foodItem.getName(), foodItem.getRestaurantId());
    return new ResponseEntity<>(new MessageDTO(Constants.FOOD_ITEM_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Deletes a food item from the database.
   *
   * @param id the ID of the food item to be deleted
   * @return a {@link ResponseEntity} with a {@link MessageDTO} indicating success or failure
   */
  public ResponseEntity<MessageDTO> deleteFoodItem(Long id) {
    FoodItem foodItem = foodItemRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + id));

    foodItemRepository.delete(foodItem);
    log.info("Food item with ID: {} deleted successfully", id);
    return new ResponseEntity<>(new MessageDTO(Constants.FOOD_ITEM_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);
  }

  /**
   * Uploads an image for a specific food item.
   * <p>
   * Validates the image format and updates the image in the database.
   * </p>
   *
   * @param foodItemImageDTO the DTO containing the food item ID and image
   * @return a {@link ResponseEntity} with a success message
   */
  public ResponseEntity<String> uploadFoodItemImage(FoodItemImageDTO foodItemImageDTO) {
    FoodItem foodItem = foodItemRepository.findById(foodItemImageDTO.getFoodItemId())
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + foodItemImageDTO.getFoodItemId()));

    validateImageFormat(foodItemImageDTO.getImage());

    try {
      foodItem.setImage(foodItemImageDTO.getImage().getBytes());
      foodItemRepository.save(foodItem);
      log.info("Image uploaded for food item with ID: {}", foodItem.getId());
    } catch (IOException e) {
      log.error("Error uploading image for food item with ID: {}", foodItem.getId(), e);
      throw new InvalidInputException("Error uploading image");
    }

    return new ResponseEntity<>(Constants.IMAGE_ADDED_SUCCESSFULLY, HttpStatus.OK);
  }

  /**
   * Retrieves the image of a specific food item.
   *
   * @param foodItemId the ID of the food item
   * @return a {@link ResponseEntity} containing the image as a byte array
   */
  public ResponseEntity<byte[]> getFoodItemImage(Long foodItemId) {
    FoodItem foodItem = foodItemRepository.findById(foodItemId)
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + foodItemId));

    byte[] image = foodItem.getImage();
    if (image == null) {
      throw new ResourceNotFoundException("No image found for food item ID: " + foodItemId);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);

    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }

  /**
   * Updates the image of a specific food item.
   * <p>
   * Validates the image format and updates the image in the database.
   * </p>
   *
   * @param foodItemImageDTO the DTO containing the food item ID and new image
   * @return a {@link ResponseEntity} with a success message
   */
  public ResponseEntity<MessageDTO> updateFoodItemImage(FoodItemImageDTO foodItemImageDTO) {
    FoodItem foodItem = foodItemRepository.findById(foodItemImageDTO.getFoodItemId())
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + foodItemImageDTO.getFoodItemId()));

    validateImageFormat(foodItemImageDTO.getImage());

    try {
      foodItem.setImage(foodItemImageDTO.getImage().getBytes());
      foodItemRepository.save(foodItem);
      log.info("Image updated for food item with ID: {}", foodItem.getId());
    } catch (IOException e) {
      log.error("Error updating image for food item with ID: {}", foodItem.getId(), e);
      throw new InvalidInputException("Error updating image");
    }

    return new ResponseEntity<>(new MessageDTO(Constants.IMAGE_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Deletes the image of a specific food item.
   *
   * @param foodItemId the ID of the food item
   * @return a {@link ResponseEntity} with a success message
   */
  public ResponseEntity<MessageDTO> deleteFoodItemImage(Long foodItemId) {
    FoodItem foodItem = foodItemRepository.findById(foodItemId)
      .orElseThrow(() -> new ResourceNotFoundException("Food item not found with ID: " + foodItemId));

    if (foodItem.getImage() == null) {
      throw new ResourceNotFoundException("No image to delete for food item ID: " + foodItemId);
    }

    foodItem.setImage(null);
    foodItemRepository.save(foodItem);
    log.info("Image deleted for food item with ID: {}", foodItemId);

    return new ResponseEntity<>(new MessageDTO(Constants.IMAGE_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves all food items sorted by price.
   * <p>
   * Supports both ascending and descending order.
   * </p>
   *
   * @param ascending true for ascending order, false for descending
   * @return a {@link ResponseEntity} containing a list of sorted {@link FoodItemOutDTO}
   */
  public ResponseEntity<List<FoodItemOutDTO>> getFoodItemsSortedByPrice(boolean ascending) {
    List<FoodItem> sortedFoodItems;
    if (ascending) {
      sortedFoodItems = foodItemRepository.findAllByOrderByPriceAsc();
    } else {
      sortedFoodItems = foodItemRepository.findAllByOrderByPriceDesc();
    }

    List<FoodItemOutDTO> foodItemOutDTOs = sortedFoodItems.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Retrieved {} food items sorted by price in {} order", sortedFoodItems.size(), ascending ? "ascending" : "descending");
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }

  /**
   * Filters food items based on their availability status.
   *
   * @param availability true for available items, false for unavailable items
   * @return a {@link ResponseEntity} containing a list of {@link FoodItemOutDTO} filtered by availability
   */
  public ResponseEntity<List<FoodItemOutDTO>> filterByAvailability(boolean availability) {
    List<FoodItem> foodItems = foodItemRepository.findByAvailability(availability);
    List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Filtered {} food items by availability: {}", foodItems.size(), availability);
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }

  /**
   * Filters food items based on their vegetarian status.
   *
   * @param isVeg true for vegetarian items, false for non-vegetarian items
   * @return a {@link ResponseEntity} containing a list of {@link FoodItemOutDTO} filtered by vegetarian status
   */
  public ResponseEntity<List<FoodItemOutDTO>> filterByIsVeg(boolean isVeg) {
    List<FoodItem> foodItems = foodItemRepository.findByIsVeg(isVeg);
    List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Filtered {} food items by vegetarian status: {}", foodItems.size(), isVeg);
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }

  /**
   * Filters food items based on the category name.
   *
   * @param categoryName the name of the category to filter by
   * @return a {@link ResponseEntity} containing a list of {@link FoodItemOutDTO} filtered by category name
   */

  public ResponseEntity<List<FoodItemOutDTO>> filterByCategoryName(String categoryName) {
    List<Category> categories = categoryRepository.findByNameIgnoreCase(categoryName);
    if (categories.isEmpty()) {
      log.warn("No categories found with name: {}", categoryName);
      throw new ResourceNotFoundException("No categories found with name: " + categoryName);
    }

    List<Long> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
    List<FoodItem> foodItems = foodItemRepository.findByCategoryIdIn(categoryIds);
    List<FoodItemOutDTO> foodItemOutDTOs = foodItems.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Filtered {} food items by category name: {}", foodItems.size(), categoryName);
    return new ResponseEntity<>(foodItemOutDTOs, HttpStatus.OK);
  }

  /**
   * Validates the format of an image file.
   * <p>
   * Only JPEG, JPG, and PNG formats are allowed.
   * </p>
   *
   * @param file the image file to be validated
   * @throws InvalidInputException if the file format is not allowed
   */
  void validateImageFormat(MultipartFile file) {
    String contentType = file.getContentType();
    if (!"image/jpeg".equals(contentType) && !"image/jpg".equals(contentType) && !"image/png".equals(contentType)) {
      log.warn("Invalid image format: {}", contentType);
      throw new InvalidInputException("Invalid file format. Only JPEG, JPG, and PNG are allowed.");
    }
  }

  /**
   * Converts a {@link FoodItem} entity to a {@link FoodItemOutDTO}.
   *
   * @param foodItem the food item entity to be converted
   * @return the corresponding {@link FoodItemOutDTO}
   */

  private FoodItemOutDTO toOutDTO(FoodItem foodItem) {
    FoodItemOutDTO dto = new FoodItemOutDTO();
    dto.setId(foodItem.getId());
    dto.setCategoryId(foodItem.getCategoryId());
    dto.setRestaurantId(foodItem.getRestaurantId());
    dto.setName(foodItem.getName());
    dto.setPrice(foodItem.getPrice());
    dto.setAvailability(foodItem.isAvailability());
    dto.setVeg(foodItem.isVeg());
    dto.setDescription(foodItem.getDescription());
    return dto;
  }
}
