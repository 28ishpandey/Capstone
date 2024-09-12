package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.CategoryInDTO;
import com.food.restaurant.dto.CategoryOutDTO;
import com.food.restaurant.entity.Category;
import com.food.restaurant.exception.ResourceAlreadyExistException;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.repository.CategoryRepository;
import com.food.restaurant.repository.RestaurantRepository;
import com.food.restaurant.util.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing Category operations such as creating, retrieving, updating,
 * and deleting categories associated with a restaurant.
 */
@Service
@Slf4j
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  /**
   * Creates a new category for a restaurant. If the restaurant does not exist, or the category name
   * already exists for the restaurant, an exception is thrown.
   *
   * @param categoryInDTO the category data transfer object containing the category name and restaurant ID.
   * @return a ResponseEntity containing a success message and HTTP status code.
   * @throws ResourceNotFoundException     if the restaurant is not found.
   * @throws ResourceAlreadyExistException if the category name already exists for the specified restaurant.
   */
  public ResponseEntity<MessageDTO> createCategory(CategoryInDTO categoryInDTO) {
    if (!restaurantRepository.existsById(categoryInDTO.getRestaurantId())) {
      log.warn("Restaurant not found with ID: {}", categoryInDTO.getRestaurantId());
      throw new ResourceNotFoundException("Restaurant not found with ID: " + categoryInDTO.getRestaurantId());
    }

    String categoryName = categoryInDTO.getName().trim().toLowerCase();
    boolean exists = categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(), categoryName);

    if (exists) {
      log.warn("Category name '{}' already exists for restaurant ID: {}", categoryInDTO.getName(), categoryInDTO.getRestaurantId());
      throw new ResourceAlreadyExistException("Category name already exists for the specified restaurant.");
    }

    Category category = new Category();
    category.setRestaurantId(categoryInDTO.getRestaurantId());
    category.setName(categoryName);
    categoryRepository.save(category);

    log.info("Category '{}' created successfully for restaurant ID: {}", category.getName(), category.getRestaurantId());
    return new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
  }

  /**
   * Retrieves all categories associated with a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant whose categories are to be retrieved.
   * @return a ResponseEntity containing a list of CategoryOutDTO objects and an HTTP status code.
   */
  public ResponseEntity<List<CategoryOutDTO>> getAllCategoriesForRestaurant(Long restaurantId) {
    List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
    List<CategoryOutDTO> categoryOutDTOs = categories.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Retrieved {} categories for restaurant ID: {}", categories.size(), restaurantId);
    return new ResponseEntity<>(categoryOutDTOs, HttpStatus.OK);
  }

  /**
   * Updates an existing category for a restaurant. If the restaurant or category is not found, or
   * if the category name already exists, an exception is thrown.
   *
   * @param id            the ID of the category to be updated.
   * @param categoryInDTO the updated category data transfer object.
   * @return a ResponseEntity containing a success message and HTTP status code.
   * @throws ResourceNotFoundException     if the restaurant or category is not found.
   * @throws ResourceAlreadyExistException if the category name already exists for the restaurant.
   */
  public ResponseEntity<MessageDTO> updateCategory(Long id, CategoryInDTO categoryInDTO) {
    log.info("Entering updateCategory with ID: {}", id);

    if (!restaurantRepository.existsById(categoryInDTO.getRestaurantId())) {
      log.warn("Restaurant not found with ID: {}", categoryInDTO.getRestaurantId());
      throw new ResourceNotFoundException("Restaurant not found with ID: " + categoryInDTO.getRestaurantId());
    }
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

    String categoryName = categoryInDTO.getName().trim().toLowerCase();

    boolean exists = categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(), categoryName);

    if (exists && !categoryName.equalsIgnoreCase(category.getName())) {
      log.warn("Category name '{}' already exists for restaurant ID: {}", categoryInDTO.getName(), categoryInDTO.getRestaurantId());
      throw new ResourceAlreadyExistException("Category name already exists for the specified restaurant.");
    }
    category.setName(categoryName);
    categoryRepository.save(category);

    log.info("Category '{}' updated successfully for restaurant ID: {}", category.getName(), category.getRestaurantId());
    return new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Deletes a category by its ID. If the category is not found, an exception is thrown.
   *
   * @param id the ID of the category to be deleted.
   * @return a ResponseEntity containing a success message and HTTP status code.
   * @throws ResourceNotFoundException if the category is not found.
   */
  public ResponseEntity<MessageDTO> deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

    categoryRepository.delete(category);
    log.info("Category with ID: {} deleted successfully", id);
    return new ResponseEntity<>(new MessageDTO(Constants.CATEGORY_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);
  }

  /**
   * Converts a Category entity to a CategoryOutDTO object.
   *
   * @param category the Category entity to convert.
   * @return the corresponding CategoryOutDTO object.
   */
  private CategoryOutDTO toOutDTO(Category category) {
    CategoryOutDTO dto = new CategoryOutDTO();
    dto.setId(category.getId());
    dto.setRestaurantId(category.getRestaurantId());
    dto.setName(category.getName());
    return dto;
  }
}
