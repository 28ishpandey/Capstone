package com.food.restaurant.repository;

import com.food.restaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Category entities.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

  /**
   * Finds all categories associated with a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant.
   * @return a list of categories belonging to the specified restaurant.
   */
  List<Category> findByRestaurantId(Long restaurantId);

  /**
   * Checks if a category with a specific name exists for a given restaurant.
   * The comparison is case-insensitive.
   *
   * @param restaurantId the ID of the restaurant.
   * @param name         the name of the category.
   * @return true if the category exists, false otherwise.
   */
  boolean existsByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);

  /**
   * Finds a category by its name for a specific restaurant.
   * The comparison is case-insensitive.
   *
   * @param restaurantId the ID of the restaurant.
   * @param name         the name of the category.
   * @return the category if found, null otherwise.
   */
  Category findByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);

  /**
   * Finds all categories with a given name, case-insensitive.
   *
   * @param categoryName the name of the category.
   * @return a list of categories with the specified name.
   */
  List<Category> findByNameIgnoreCase(String categoryName);
}
