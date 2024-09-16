package com.food.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing an Order in the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

  /**
   * The ID of the order, generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  /**
   * The ID of the user who placed the order.
   */
  @Column(nullable = false)
  private Long userId;

  /**
   * The ID of the restaurant from which the order was placed.
   */
  @Column(nullable = false)
  private Long restaurantId;

  /**
   * The current status of the order.
   */
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  /**
   * The timestamp when the order was placed.
   */
  private LocalDateTime placedAt;

  /**
   * The timestamp when the order was last updated.
   */
  private LocalDateTime updatedAt;

  /**
   * The total amount for the order.
   */
  @Column(nullable = false)
  private Double totalAmount;
}
