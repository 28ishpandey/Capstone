package com.food.restaurant.repository;

import com.food.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Restaurant entities.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  /**
   * Finds a restaurant by its email.
   *
   * @param email the email of the restaurant.
   * @return an Optional containing the restaurant if found, or empty if not.
   */
  Optional<Restaurant> findByEmail(String email);

  /**
   * Checks if a restaurant exists by its email.
   *
   * @param email the email of the restaurant.
   * @return true if a restaurant exists with the specified email, false otherwise.
   */
  boolean existsByEmail(String email);

  /**
   * Checks if a restaurant exists by its name (case-insensitive).
   *
   * @param name the name of the restaurant.
   * @return true if a restaurant exists with the specified name, false otherwise.
   */
  boolean existsByNameIgnoreCase(String name);

  /**
   * Finds all restaurants owned by a specific restaurant owner.
   *
   * @param ownerId the ID of the restaurant owner.
   * @return a list of restaurants belonging to the specified owner.
   */
  List<Restaurant> findByRestaurantOwnerId(Long ownerId);
}
