package com.food.order.repository;

import com.food.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link OrderItem} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  /**
   * Finds a list of {@link OrderItem} by the order ID.
   *
   * @param orderId the ID of the order.
   * @return a list of {@link OrderItem}.
   */
  List<OrderItem> findByOrderId(Long orderId);

  /**
   * Finds an {@link OrderItem} by both order ID and food item ID.
   *
   * @param orderId the ID of the order.
   * @param foodItemId the ID of the food item.
   * @return an {@link Optional} containing the {@link OrderItem} if found.
   */
  Optional<OrderItem> findByOrderIdAndFoodItemId(Long orderId, Long foodItemId);
}
