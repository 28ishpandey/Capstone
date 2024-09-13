package com.food.restaurant.repository;

import com.food.restaurant.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing FoodItem entities.
 */
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

  /**
   * Checks if a food item with a specific name exists for a given restaurant.
   * The comparison is case-insensitive.
   *
   * @param restaurantId the ID of the restaurant.
   * @param name         the name of the food item.
   * @return true if the food item exists, false otherwise.
   */
  boolean existsByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);

  /**
   * Finds all food items associated with a specific category.
   *
   * @param categoryId the ID of the category.
   * @return a list of food items belonging to the specified category.
   */
  List<FoodItem> findByCategoryId(Long categoryId);

  /**
   * Finds all food items ordered by price in ascending order.
   *
   * @return a list of food items ordered by price (ascending).
   */
  List<FoodItem> findAllByOrderByPriceAsc();

  /**
   * Finds all food items ordered by price in descending order.
   *
   * @return a list of food items ordered by price (descending).
   */
  List<FoodItem> findAllByOrderByPriceDesc();

  /**
   * Finds all food items by their availability status.
   *
   * @param availability the availability status (true for available, false for unavailable).
   * @return a list of food items with the specified availability.
   */
  List<FoodItem> findByAvailability(boolean availability);

  /**
   * Finds all food items by their vegetarian status.
   *
   * @param isVeg the vegetarian status (true for vegetarian, false for non-vegetarian).
   * @return a list of food items with the specified vegetarian status.
   */
  List<FoodItem> findByIsVeg(boolean isVeg);

  /**
   * Finds all food items belonging to any of the specified category IDs.
   *
   * @param categoryIds a list of category IDs.
   * @return a list of food items belonging to the specified categories.
   */
  List<FoodItem> findByCategoryIdIn(List<Long> categoryIds);

  /**
   * Finds all food items associated with a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant.
   * @return a list of food items belonging to the specified restaurant.
   */
  List<FoodItem> findByRestaurantId(Long restaurantId);
}
