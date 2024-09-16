package com.food.restaurant.repository;

import com.food.restaurant.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing RestaurantOwner entities.
 */
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {

  /**
   * Checks if a restaurant owner exists by their email.
   *
   * @param email the email of the restaurant owner.
   * @return true if a restaurant owner exists with the specified email, false otherwise.
   */
  boolean existsByEmail(String email);

  /**
   * Finds a restaurant owner by their email.
   *
   * @param email the email of the restaurant owner.
   * @return an Optional containing the restaurant owner if found, or empty if not.
   */
  Optional<RestaurantOwner> findByEmail(String email);
}
