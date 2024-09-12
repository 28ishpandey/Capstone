package com.food.user.repository;

import com.food.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing user entities.
 */
public interface UserRepository extends JpaRepository<Users, Long> {
  Optional<Users> findByEmailIgnoreCase(String email);
}
