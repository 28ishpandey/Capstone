package com.food.restaurant.controller;

import com.food.restaurant.dto.FoodItemImageDTO;
import com.food.restaurant.dto.FoodItemInDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.service.FoodItemService;
import com.food.restaurant.util.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The {@code FoodItemController} class handles HTTP requests related to food items,
 * including creation, updating, deletion, retrieval, and image management.
 */
@RestController
@RequestMapping("/food-items")
@Slf4j
public class FoodItemController {
  /**
   * Service for handling food-related operations.
   */
  @Autowired
  private FoodItemService foodItemService;

  /**
   * Creates a new food item.
   *
   * @param foodItemInDTO the DTO containing food item details
   * @param image         the image file for the food item (optional)
   * @return a {@link ResponseEntity} with a message indicating success or failure
   */
  @PostMapping
  public ResponseEntity<MessageDTO> createFoodItem(@Validated @ModelAttribute final FoodItemInDTO foodItemInDTO,
                                                   @RequestPart(value = "image", required = false)
                                                   final MultipartFile image) {
    log.info("Received request to create food item for restaurant ID: {}", foodItemInDTO.getRestaurantId());
    return foodItemService.createFoodItem(foodItemInDTO, image);
  }

  /**
   * Retrieves all food items for a given category.
   *
   * @param categoryId the ID of the category
   * @return a {@link ResponseEntity} with a list of food items for the specified category
   */
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<FoodItemOutDTO>> getAllFoodItems(@PathVariable final Long categoryId) {
    log.info("Received request to get all food items for category ID: {}", categoryId);
    return foodItemService.getAllFoodItemsForCategory(categoryId);
  }

  /**
   * Updates a food item by its ID.
   *
   * @param id            the ID of the food item to update
   * @param foodItemInDTO the DTO containing updated food item details
   * @param image         the new image file for the food item (optional)
   * @return a {@link ResponseEntity} with a message indicating success or failure
   */
  @PutMapping("/{id}")
  public ResponseEntity<MessageDTO> updateFoodItem(
    @PathVariable final Long id,
    @Validated @ModelAttribute final FoodItemInDTO foodItemInDTO,
    @RequestParam(value = "image", required = false) final MultipartFile image) {
    log.info("Received request to update food item with ID: {}", id);
    return foodItemService.updateFoodItem(id, foodItemInDTO, image);
  }

  /**
   * Deletes a food item by its ID.
   *
   * @param id the ID of the food item to delete
   * @return a {@link ResponseEntity} with a message indicating success or failure
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDTO> deleteFoodItem(@PathVariable final Long id) {
    log.info("Received request to delete food item with ID: {}", id);
    return foodItemService.deleteFoodItem(id);
  }

  /**
   * Uploads an image for a food item.
   *
   * @param foodItemImageDTO the DTO containing the food item ID and image file
   * @return a {@link ResponseEntity} with a success message
   */
  @PostMapping("/upload-image")
  public ResponseEntity<String> uploadFoodItemImage(@ModelAttribute final FoodItemImageDTO foodItemImageDTO) {
    log.info("Received request to upload image for food item ID: {}", foodItemImageDTO.getFoodItemId());
    return foodItemService.uploadFoodItemImage(foodItemImageDTO);
  }

  /**
   * Retrieves the image for a food item by its ID.
   *
   * @param foodItemId the ID of the food item
   * @return a {@link ResponseEntity} containing the image as a byte array
   */
  @GetMapping("/{foodItemId}/image")
  public ResponseEntity<byte[]> getFoodItemImage(@PathVariable final Long foodItemId) {
    log.info("Received request to get image for food item ID: {}", foodItemId);
    return foodItemService.getFoodItemImage(foodItemId);
  }

  /**
   * Updates the image for a food item.
   *
   * @param foodItemImageDTO the DTO containing the food item ID and new image file
   * @return a {@link ResponseEntity} with a message indicating success or failure
   */
  @PutMapping("/{foodItemId}/image")
  public ResponseEntity<MessageDTO> updateFoodItemImage(@ModelAttribute final FoodItemImageDTO foodItemImageDTO) {
    log.info("Received request to update image for food item ID: {}", foodItemImageDTO.getFoodItemId());
    return foodItemService.updateFoodItemImage(foodItemImageDTO);
  }

  /**
   * Deletes the image for a food item by its ID.
   *
   * @param foodItemId the ID of the food item
   * @return a {@link ResponseEntity} with a message indicating success or failure
   */
  @DeleteMapping("/{foodItemId}/image")
  public ResponseEntity<MessageDTO> deleteFoodItemImage(@PathVariable final Long foodItemId) {
    log.info("Received request to delete image for food item ID: {}", foodItemId);
    return foodItemService.deleteFoodItemImage(foodItemId);
  }

  /**
   * Retrieves food items sorted by price in ascending or descending order.
   *
   * @param ascending whether the items should be sorted in ascending (true) or descending (false) order
   * @return a {@link ResponseEntity} with a list of sorted food items
   */
  @GetMapping("/sort")
  public ResponseEntity<List<FoodItemOutDTO>> getFoodItemsSortedByPrice(
    @RequestParam(name = "asc", defaultValue = "true") final boolean ascending) {
    log.info("Received request to get food items sorted by price in {} order", ascending ? "ascending" : "descending");
    return foodItemService.getFoodItemsSortedByPrice(ascending);
  }

  /**
   * Filters food items by availability.
   *
   * @param availability the availability status to filter by
   * @return a {@link ResponseEntity} with a list of food items matching the availability status
   */
  @GetMapping("/filter/availability")
  public ResponseEntity<List<FoodItemOutDTO>> filterByAvailability(@RequestParam final boolean availability) {
    log.info("Received request to filter food items by availability: {}", availability);
    return foodItemService.filterByAvailability(availability);
  }

  /**
   * Filters food items by vegetarian status.
   *
   * @param isVeg the vegetarian status to filter by
   * @return a {@link ResponseEntity} with a list of food items matching the vegetarian status
   */
  @GetMapping("/filter/is-veg")
  public ResponseEntity<List<FoodItemOutDTO>> filterByIsVeg(@RequestParam final boolean isVeg) {
    log.info("Received request to filter food items by vegetarian status: {}", isVeg);
    return foodItemService.filterByIsVeg(isVeg);
  }

  /**
   * Filters food items by category name.
   *
   * @param categoryName the name of the category to filter by
   * @return a {@link ResponseEntity} with a list of food items matching the category name
   */
  @GetMapping("/filter/category")
  public ResponseEntity<List<FoodItemOutDTO>> filterByCategoryName(@RequestParam final String categoryName) {
    log.info("Received request to filter food items by category name: {}", categoryName);
    return foodItemService.filterByCategoryName(categoryName);
  }
}
