package com.food.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Entity class representing an item in an order.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  /**
   * The ID of the order item, generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;

  /**
   * The ID of the order to which this item belongs.
   */
  @Column(nullable = false)
  private Long orderId;

  /**
   * The ID of the food item.
   */
  @Column(nullable = false)
  private Long foodItemId;

  /**
   * The name of the food item.
   */
  private String foodItemName;

  /**
   * The quantity of the item in the order.
   */
  @Column(nullable = false)
  private Integer quantity;

  /**
   * The price of the item.
   */
  @Column(nullable = false)
  private Double price;
}
