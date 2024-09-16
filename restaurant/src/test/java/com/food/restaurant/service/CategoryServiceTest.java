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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private RestaurantRepository restaurantRepository;

  @InjectMocks
  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createCategory_Success() {
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Test Category");
    categoryInDTO.setRestaurantId(1L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(true);
    when(categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(),
      categoryInDTO.getName().trim().toLowerCase())).thenReturn(false);

    ResponseEntity<MessageDTO> response = categoryService.createCategory(categoryInDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(Constants.CATEGORY_CREATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(categoryRepository).save(any(Category.class));
  }

  @Test
  void createCategory_RestaurantNotFound() {
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setRestaurantId(999L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> categoryService.createCategory(categoryInDTO));
    verify(categoryRepository, never()).save(any(Category.class));
  }

  @Test
  void createCategory_CategoryNameExists() {
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Existing Category");
    categoryInDTO.setRestaurantId(1L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(true);
    when(categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(),
      categoryInDTO.getName().trim().toLowerCase())).thenReturn(true);

    assertThrows(ResourceAlreadyExistException.class, () -> categoryService.createCategory(categoryInDTO));
    verify(categoryRepository, never()).save(any(Category.class));
  }

  @Test
  void getAllCategoriesForRestaurant_Success() {
    Long restaurantId = 1L;
    List<Category> categories = Arrays.asList(new Category(), new Category());

    when(categoryRepository.findByRestaurantId(restaurantId)).thenReturn(categories);

    ResponseEntity<List<CategoryOutDTO>> response = categoryService.getAllCategoriesForRestaurant(restaurantId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void getAllCategoriesForRestaurant_NoCategories() {
    Long restaurantId = 1L;

    when(categoryRepository.findByRestaurantId(restaurantId)).thenReturn(Arrays.asList());

    ResponseEntity<List<CategoryOutDTO>> response = categoryService.getAllCategoriesForRestaurant(restaurantId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
  }

  @Test
  void updateCategory_Success() {
    Long id = 1L;
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Updated Category");
    categoryInDTO.setRestaurantId(1L);
    Category existingCategory = new Category();
    existingCategory.setId(id);
    existingCategory.setName("Old Category");
    existingCategory.setRestaurantId(1L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(true);
    when(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
    when(categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(),
      categoryInDTO.getName().trim().toLowerCase())).thenReturn(false);

    ResponseEntity<MessageDTO> response = categoryService.updateCategory(id, categoryInDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.CATEGORY_UPDATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(categoryRepository).save(existingCategory);
  }

  @Test
  void updateCategory_CategoryNotFound() {
    Long id = 999L;
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setRestaurantId(1L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(true);
    when(categoryRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(id, categoryInDTO));
    verify(categoryRepository, never()).save(any(Category.class));
  }

  @Test
  void updateCategory_NameAlreadyExists() {
    Long id = 1L;
    CategoryInDTO categoryInDTO = new CategoryInDTO();
    categoryInDTO.setName("Existing Category");
    categoryInDTO.setRestaurantId(1L);
    Category existingCategory = new Category();
    existingCategory.setId(id);
    existingCategory.setName("Old Category");
    existingCategory.setRestaurantId(1L);

    when(restaurantRepository.existsById(categoryInDTO.getRestaurantId())).thenReturn(true);
    when(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
    when(categoryRepository.existsByRestaurantIdAndNameIgnoreCase(categoryInDTO.getRestaurantId(),
      categoryInDTO.getName().trim().toLowerCase())).thenReturn(true);

    assertThrows(ResourceAlreadyExistException.class, () -> categoryService.updateCategory(id, categoryInDTO));
    verify(categoryRepository, never()).save(any(Category.class));
  }

  @Test
  void deleteCategory_Success() {
    Long id = 1L;
    Category category = new Category();
    category.setId(id);

    when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

    ResponseEntity<MessageDTO> response = categoryService.deleteCategory(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(Constants.CATEGORY_DELETED_SUCCESSFULLY, response.getBody().getMessage());
    verify(categoryRepository).delete(category);
  }

  @Test
  void deleteCategory_NotFound() {
    Long id = 999L;

    when(categoryRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(id));
    verify(categoryRepository, never()).delete(any(Category.class));
  }
}