package com.food.restaurant.controller;

import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.dto.FoodItemOutDTO;
import com.food.restaurant.dto.RestaurantDetailsDTO;
import com.food.restaurant.dto.RestaurantImageDTO;
import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantUpdateDTO;
import com.food.restaurant.service.RestaurantService;
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
 * The RestaurantController class handles incoming HTTP requests related to
 * restaurant management, including creating, updating, deleting, and retrieving
 * restaurants, along with handling images and restaurant status.
 */
@RestController
@RequestMapping("/restaurants")
@Slf4j
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  /**
   * Creates a new restaurant.
   *
   * @param restaurantInDto the restaurant data transfer object containing the restaurant's details.
   * @param image an optional image file for the restaurant.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @PostMapping
  public ResponseEntity<MessageDTO> createRestaurant(
    @ModelAttribute RestaurantInDTO restaurantInDto,
    @RequestPart(value = "image", required = false) MultipartFile image) {
    log.info("Received request to create restaurant with email: {}", restaurantInDto.getEmail());
    return restaurantService.createRestaurant(restaurantInDto, image);
  }

  /**
   * Updates an existing restaurant.
   *
   * @param id the ID of the restaurant to be updated.
   * @param restaurantUpdateDto the data transfer object containing the updated restaurant details.
   * @param image an optional image file to update the restaurant's image.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @PutMapping("/{id}")
  public ResponseEntity<MessageDTO> updateRestaurant(
    @PathVariable Long id,
    @Validated @ModelAttribute RestaurantUpdateDTO restaurantUpdateDto,
    @RequestPart(value = "image", required = false) MultipartFile image) {
    log.info("Received request to update restaurant with id: {} and email: {}", id, restaurantUpdateDto.getEmail());
    return restaurantService.updateRestaurant(id, restaurantUpdateDto, image);
  }

  /**
   * Deletes an existing restaurant.
   *
   * @param id the ID of the restaurant to be deleted.
   * @param ownerPassword the password of the restaurant owner for validation.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDTO> deleteRestaurant(@PathVariable Long id, @RequestParam String ownerPassword) {
    log.info("Received request to delete restaurant with id: {} and owner password provided", id);
    return restaurantService.deleteRestaurant(id, ownerPassword);
  }

  /**
   * Retrieves the details of a restaurant by its ID.
   *
   * @param id the ID of the restaurant.
   * @return ResponseEntity containing restaurant details.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getRestaurant(@PathVariable Long id) {
    log.info("Received request to get restaurant with id: {}", id);
    return restaurantService.getRestaurant(id);
  }

  /**
   * Retrieves a list of all restaurants.
   *
   * @return ResponseEntity containing a list of all restaurants.
   */
  @GetMapping
  public ResponseEntity<List<RestaurantOutDTO>> getAllRestaurants() {
    log.info("Received request to get all restaurants");
    return restaurantService.getAllRestaurants();
  }

  /**
   * Uploads an image for a specific restaurant.
   *
   * @param restaurantImageDTO the data transfer object containing the restaurant ID and the image file.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @PostMapping("/upload-image")
  public ResponseEntity<String> uploadRestaurantImage(@ModelAttribute RestaurantImageDTO restaurantImageDTO) {
    log.info("Received request to upload image for restaurant with ID: {}", restaurantImageDTO.getRestaurantId());
    return restaurantService.uploadRestaurantImage(restaurantImageDTO);
  }

  /**
   * Retrieves the image of a specific restaurant by its ID.
   *
   * @param restaurantId the ID of the restaurant.
   * @return ResponseEntity containing the restaurant image as a byte array.
   */
  @GetMapping("/{restaurantId}/image")
  public ResponseEntity<byte[]> getRestaurantImage(@PathVariable Long restaurantId) {
    log.info("Received request to get image for restaurant with ID: {}", restaurantId);
    return restaurantService.getRestaurantImage(restaurantId);
  }

  /**
   * Updates the image of a specific restaurant.
   *
   * @param restaurantImageDTO the data transfer object containing the restaurant ID and the new image.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @PutMapping("/{restaurantId}/image")
  public ResponseEntity<MessageDTO> updateRestaurantImage(@ModelAttribute RestaurantImageDTO restaurantImageDTO) {
    log.info("Received request to update image for restaurant with ID: {}", restaurantImageDTO.getRestaurantId());
    return restaurantService.updateRestaurantImage(restaurantImageDTO);
  }

  /**
   * Deletes the image of a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant.
   * @return ResponseEntity containing a message and HTTP status.
   */
  @DeleteMapping("/{restaurantId}/image")
  public ResponseEntity<String> deleteRestaurantImage(@PathVariable Long restaurantId) {
    log.info("Received request to delete image for restaurant with ID: {}", restaurantId);
    return restaurantService.deleteRestaurantImage(restaurantId);
  }

  /**
   * Sets the open status of a specific restaurant.
   *
   * @param id the ID of the restaurant.
   * @param isOpen the open status to be set (true or false).
   * @return ResponseEntity containing a message and HTTP status.
   */
  @PutMapping("/{id}/open-status")
  public ResponseEntity<MessageDTO> setRestaurantStatus(@PathVariable Long id, @RequestParam Boolean isOpen) {
    log.info("Received request to set restaurant status with id: {} to isOpen: {}", id, isOpen);
    return restaurantService.setRestaurantStatus(id, isOpen);
  }

  /**
   * Retrieves all categories and their food items for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant.
   * @return ResponseEntity containing a list of categories and their food items.
   */
  @GetMapping("/{restaurantId}/categories/food-items")
  public ResponseEntity<List<CategoryOutDTO>> getAllFoodItemsByRestaurant(@PathVariable Long restaurantId) {
    log.info("Received request to fetch all categories and their food items for restaurant ID: {}", restaurantId);
    return restaurantService.getAllFoodItemsByRestaurant(restaurantId);
  }

  /**
   * Retrieves all food items for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant.
   * @return ResponseEntity containing a list of food items.
   */
  @GetMapping("/{restaurantId}/food-items")
  public ResponseEntity<List<FoodItemOutDTO>> getAllFoodItemsOfRestaurant(@PathVariable Long restaurantId) {
    log.info("Received request to fetch all food items for restaurant ID: {}", restaurantId);
    return restaurantService.getAllFoodItemsOfRestaurant(restaurantId);
  }

  /**
   * Retrieves all restaurants along with their categories and food items.
   *
   * @return ResponseEntity containing a list of all restaurants with their details.
   */
  @GetMapping("/all-details")
  public ResponseEntity<List<RestaurantDetailsDTO>> getAllRestaurantsWithDetails() {
    log.info("Received request to get all restaurants with their categories and food items");
    return restaurantService.getAllRestaurantsWithDetails();
  }
}
