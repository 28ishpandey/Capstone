package com.food.order.constants;

/**
 * The {@code Constants} class holds constant values for response messages and error messages
 * used throughout the food order system. These constants are intended for use in various
 * service and controller classes to provide standardized messages for logging, exceptions,
 * and responses.
 */
public class Constants {

  /**
   * Message indicating that an order has been cancelled successfully.
   */
  public static final String ORDER_CANCELLED_SUCCESSFULLY = "Order cancelled successfully.";

  /**
   * Message indicating that an order was not found by its ID.
   * The ID should be appended to this string when used.
   */
  public static final String ORDER_NOT_FOUND = "Order not found with ID: ";

  /**
   * Message indicating that a user was not found by their ID.
   * The ID should be appended to this string when used.
   */
  public static final String USER_NOT_FOUND = "User not found with ID: ";

  /**
   * Message indicating that a restaurant was not found by its ID.
   * The ID should be appended to this string when used.
   */
  public static final String RESTAURANT_NOT_FOUND = "Restaurant not found with ID: ";

  /**
   * Message indicating that an order has an invalid status.
   */
  public static final String INVALID_ORDER_STATUS = "Invalid order status";

  /**
   * Message indicating that the order cancellation window has passed.
   * Orders can only be cancelled within a specified time frame, usually
   * set to 30 seconds.
   */
  public static final String ORDER_CANCELLATION_TIME_EXCEEDED = "Orders can only be cancelled within 30 seconds of placement";

  /**
   * Message indicating that the order status has been updated successfully.
   */
  public static final String ORDER_STATUS_UPDATED_SUCCESSFULLY = "Order status updated successfully";

  /**
   * Message indicating that non-cart orders cannot be modified.
   */
  public static final String CANNOT_MODIFY_NON_CART_ORDER = "Cannot Modify Non-Cart Orders";

  /**
   * Message indicating that an item was not found in the specified order.
   */
  public static final String ITEM_NOT_FOUND_IN_ORDER = "Item Not Found in Order";

  /**
   * Message indicating that the restaurant is currently closed.
   */
  public static final String RESTAURANT_IS_CLOSED = "Restaurant is closed currently";

  /**
   * Message indicating that the user's wallet balance is insufficient for the current transaction.
   */
  public static final String INSUFFICIENT_WALLET_BALANCE = "Insufficient wallet balance";
}
