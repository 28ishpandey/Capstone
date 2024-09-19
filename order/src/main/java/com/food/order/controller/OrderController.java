package com.food.order.controller;

import com.food.order.dto.MessageDTO;
import com.food.order.dto.OrderCreateDTO;
import com.food.order.dto.OrderItemDTO;
import com.food.order.dto.OrderResponseDTO;
import com.food.order.dto.UpdateItemQuantityDTO;
import com.food.order.dto.UpdateOrderStatusDTO;
import com.food.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The {@code OrderController} class handles HTTP requests related to order operations
 * such as creating an order, updating order status, adding or removing items, and
 * fetching user or restaurant orders.
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
  /**
   * Service for handling order-related operations.
   */
  @Autowired
  private OrderService orderService;

  /**
   * Creates a new order based on the provided order details.
   *
   * @param orderCreateDTO the order creation details provided in the request body
   * @return the created order details wrapped in a {@link ResponseEntity}
   */
  @PostMapping
  public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody final OrderCreateDTO orderCreateDTO) {
    log.info("Creating order for user: {}", orderCreateDTO.getUserId());
    return orderService.createOrder(orderCreateDTO);
  }

  /**
   * Retrieves the details of an order based on the given order ID.
   *
   * @param orderId the ID of the order to retrieve
   * @return the order details wrapped in a {@link ResponseEntity}
   */
  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable final Long orderId) {
    log.info("Fetching order: {}", orderId);
    return orderService.getOrder(orderId);
  }

  /**
   * Updates the status of an existing order.
   *
   * @param orderId the ID of the order to update
   * @param updateOrderStatusDTO the new order status provided in the request body
   * @return the response indicating the update operation status
   */
  @PutMapping("/{orderId}/status")
  public ResponseEntity<?> updateOrderStatus(@PathVariable final Long orderId,
                                             @Valid @RequestBody final UpdateOrderStatusDTO updateOrderStatusDTO) {
    log.info("Updating order status: {} to {}", orderId, updateOrderStatusDTO.getStatus());
    return orderService.updateOrderStatus(orderId, updateOrderStatusDTO.getStatus());
  }

  /**
   * Retrieves all orders placed by a specific user.
   *
   * @param userId the ID of the user whose orders to retrieve
   * @return a list of orders for the specified user wrapped in a {@link ResponseEntity}
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<?> getUserOrders(@PathVariable final Long userId) {
    log.info("Fetching orders for user: {}", userId);
    return orderService.getUserOrders(userId);
  }

  /**
   * Retrieves all orders placed for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant whose orders to retrieve
   * @return a list of orders for the specified restaurant wrapped in a {@link ResponseEntity}
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<?> getRestaurantOrders(@PathVariable final Long restaurantId) {
    log.info("Fetching orders for restaurant: {}", restaurantId);
    return orderService.getRestaurantOrders(restaurantId);
  }

  /**
   * Cancels an order with the specified ID.
   *
   * @param orderId the ID of the order to cancel
   * @return a response message wrapped in a {@link ResponseEntity}
   */
  @DeleteMapping("/{orderId}")
  public ResponseEntity<MessageDTO> cancelOrder(@PathVariable final Long orderId) {
    log.info("Cancelling order: {}", orderId);
    return orderService.cancelOrder(orderId);
  }

  /**
   * Adds an item to the specified order.
   *
   * @param orderId the ID of the order to which the item will be added
   * @param addItemDTO the details of the item to add to the order
   * @return the updated order details wrapped in a {@link ResponseEntity}
   */
  @PostMapping("/{orderId}/items")
  public ResponseEntity<OrderResponseDTO> addItemToOrder(@PathVariable final Long orderId,
                                                         @Valid @RequestBody final OrderItemDTO addItemDTO) {
    log.info("Adding item to order: {}", orderId);
    return orderService.addItemToOrder(orderId, addItemDTO);
  }

  /**
   * Removes an item from the specified order.
   *
   * @param orderId the ID of the order from which the item will be removed
   * @param foodItemId the ID of the item to remove
   * @return the updated order details wrapped in a {@link ResponseEntity}
   */
  @DeleteMapping("/{orderId}/items/{foodItemId}")
  public ResponseEntity<OrderResponseDTO> removeItemFromOrder(@PathVariable final Long orderId,
                                                              @PathVariable final Long foodItemId) {
    log.info("Removing item from order: {}, foodItemId: {}", orderId, foodItemId);
    return orderService.removeItemFromOrder(orderId, foodItemId);
  }

  /**
   * Updates the quantity of a specific item in an order.
   *
   * @param orderId the ID of the order containing the item
   * @param foodItemId the ID of the item whose quantity will be updated
   * @param updateQuantityDTO the new quantity to be set for the item
   * @return the updated order details wrapped in a {@link ResponseEntity}
   */
  @PutMapping("/{orderId}/items/{foodItemId}")
  public ResponseEntity<OrderResponseDTO> updateItemQuantity(@PathVariable final Long orderId,
                                                             @PathVariable final Long foodItemId,
                                                             @Valid @RequestBody final
                                                               UpdateItemQuantityDTO updateQuantityDTO) {
    log.info("Updating item quantity in order: {}, foodItemId: {}, quantity: {}", orderId, foodItemId,
      updateQuantityDTO.getQuantity());
    return orderService.updateItemQuantity(orderId, foodItemId, updateQuantityDTO);
  }
  /**
   * Deletes an order with the specified ID.
   *
   * @param orderId the ID of the order to delete
   * @return a response message wrapped in a {@link ResponseEntity}
   */
  @DeleteMapping("/{orderId}/delete")
  public ResponseEntity<MessageDTO> deleteOrder(@PathVariable final Long orderId) {
    log.info("Deleting order: {}", orderId);
    return orderService.deleteOrder(orderId);
  }
}
