package com.food.restaurant.controller;

import com.food.restaurant.dto.CategoryInDTO;
import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.service.CategoryService;
import com.food.restaurant.util.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing categories related to restaurants.
 */
@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
  /**
   * Service for handling category-related operations.
   */
  @Autowired
  private CategoryService categoryService;

  /**
   * Create a new category for a restaurant.
   *
   * @param categoryInDTO the category input data transfer object
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  @PostMapping
  public ResponseEntity<MessageDTO> createCategory(@Validated @RequestBody final CategoryInDTO categoryInDTO) {
    log.info("Received request to create category for restaurant ID: {}", categoryInDTO.getRestaurantId());
    return categoryService.createCategory(categoryInDTO);
  }

  /**
   * Get all categories for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a ResponseEntity containing a list of category output DTOs
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<CategoryOutDTO>> getAllCategories(@PathVariable final Long restaurantId) {
    log.info("Received request to get all categories for restaurant ID: {}", restaurantId);
    return categoryService.getAllCategoriesForRestaurant(restaurantId);
  }

  /**
   * Update a specific category.
   *
   * @param id            the ID of the category to update
   * @param categoryInDTO the updated category input data transfer object
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  @PutMapping("/{id}")
  public ResponseEntity<MessageDTO> updateCategory(@PathVariable final Long id,
                                                   @Validated @RequestBody final CategoryInDTO categoryInDTO) {
    log.info("Received request to update category with ID: {}", id);
    return categoryService.updateCategory(id, categoryInDTO);
  }

  /**
   * Delete a specific category by its ID.
   *
   * @param id the ID of the category to delete
   * @return a ResponseEntity containing a message indicating the result of the operation
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDTO> deleteCategory(@PathVariable final Long id) {
    log.info("Received request to delete category with ID: {}", id);
    return categoryService.deleteCategory(id);
  }
}
