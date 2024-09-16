package com.food.order.entity;
/**
 * Enum representing the possible statuses of an order.
 */
public enum OrderStatus {
  /**
   * The order is currently in the user's cart and has not yet been placed.
   * The user can modify the order before proceeding to checkout.
   */
  IN_CART,

  /**
   * The order has been placed by the user and is being processed by the system.
   * This status indicates that the order is awaiting fulfillment or delivery.
   */
  PLACED,

  /**
   * The order has been cancelled either by the user or due to some system condition.
   * No further processing will occur for this order.
   */
  CANCELLED,

  /**
   * The order has been successfully completed and delivered to the user.
   * This is the final state of a fulfilled order.
   */
  COMPLETED
}
