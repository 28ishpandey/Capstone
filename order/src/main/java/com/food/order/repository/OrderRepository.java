package com.food.order.repository;

import com.food.order.entity.Order;
import com.food.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Order} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

  /**
   * Finds a list of {@link Order} by the user ID.
   *
   * @param userId the ID of the user.
   * @return a list of {@link Order}.
   */
  List<Order> findByUserId(Long userId);

  /**
   * Finds an {@link Order} by user ID, restaurant ID, and order status.
   *
   * @param userId the ID of the user.
   * @param restaurantId the ID of the restaurant.
   * @param orderStatus the status of the order.
   * @return an {@link Optional} containing the {@link Order} if found.
   */
  Optional<Order> findByUserIdAndRestaurantIdAndOrderStatus(Long userId, Long restaurantId, OrderStatus orderStatus);

  /**
   * Finds a list of {@link Order} by the restaurant ID.
   *
   * @param restaurantId the ID of the restaurant.
   * @return a list of {@link Order}.
   */
  List<Order> findByRestaurantId(Long restaurantId);
}
