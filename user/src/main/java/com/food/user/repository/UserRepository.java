package com.food.user.repository;

import com.food.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for {@link Users} entity.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom
 * query methods for {@link Users} entities.
 * </p>
 */
public interface UserRepository extends JpaRepository<Users, Long> {

  /**
   * Finds a {@link Users} entity by its email address, ignoring case.
   *
   * @param email the email address to search for
   * @return an {@link Optional} containing the {@link Users} entity if found, or empty otherwise
   */
  Optional<Users> findByEmailIgnoreCase(String email);
}
