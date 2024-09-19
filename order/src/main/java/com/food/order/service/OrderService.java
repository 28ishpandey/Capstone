package com.food.order.service;

import com.food.order.constants.Constants;
import com.food.order.dto.MessageDTO;
import com.food.order.dto.OrderCreateDTO;
import com.food.order.dto.OrderItemDTO;
import com.food.order.dto.OrderResponseDTO;
import com.food.order.dto.RestaurantOutDTO;
import com.food.order.dto.UpdateItemQuantityDTO;
import com.food.order.dto.UserResponseDTO;
import com.food.order.entity.Order;
import com.food.order.entity.OrderItem;
import com.food.order.entity.OrderStatus;
import com.food.order.exception.InvalidInputException;
import com.food.order.exception.ResourceNotFoundException;
import com.food.order.exception.ServiceUnavailableException;
import com.food.order.feign.RestaurantFeignClient;
import com.food.order.feign.UserFeignClient;
import com.food.order.repository.OrderItemRepository;
import com.food.order.repository.OrderRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class to handle operations related to Orders.
 *
 * It provides functionality to create, update, retrieve, and cancel orders, as well as
 * managing the items in an order. This service interacts with other microservices such
 * as User and Restaurant using Feign clients.
 */
@Service
@Slf4j
public class OrderService {
  /**
   * Repository for order data operations.
   */
  @Autowired
  private OrderRepository orderRepository;
  /**
   * Repository for order items data operations.
   */
  @Autowired
  private OrderItemRepository orderItemRepository;
  /**
   * For user microservice communication operations.
   */
  @Autowired
  private UserFeignClient userFeignClient;
  /**
   * For restaurant microservice communication operations.
   */
  @Autowired
  private RestaurantFeignClient restaurantFeignClient;
  /**
   * Creates a new order for the given user and restaurant.
   *
   * @param orderCreateDTO the DTO containing the order creation details
   * @return ResponseEntity containing the created order response
   * @throws ResourceNotFoundException if the user or restaurant is not found
   * @throws InvalidInputException if the restaurant is closed or insufficient wallet balance
   */
  public ResponseEntity<OrderResponseDTO> createOrder(final OrderCreateDTO orderCreateDTO) {
    UserResponseDTO user = getUser(orderCreateDTO.getUserId());
    RestaurantOutDTO restaurant = getRestaurant(orderCreateDTO.getRestaurantId());

    validateOrderCreation(user, restaurant, orderCreateDTO);

    Order order = createOrderEntity(orderCreateDTO, user.getUserId(), restaurant.getId());
    order = orderRepository.save(order);

    List<OrderItem> orderItems = createOrderItems(orderCreateDTO.getOrderItems(), order.getOrderId());
    orderItemRepository.saveAll(orderItems);

    log.info("Order created successfully for user ID: {} and restaurant ID: {}", order.getUserId(), order.getRestaurantId());
    return new ResponseEntity<>(createOrderResponseDTO(order, orderItems), HttpStatus.CREATED);
  }
  /**
   * Retrieves user details by their ID.
   *
   * @param userId the ID of the user
   * @return UserResponseDTO containing user details
   * @throws ResourceNotFoundException if the user is not found
   */
  private UserResponseDTO getUser(final Long userId) {
    try {
      return userFeignClient.getUserById(userId);
    } catch (FeignException.NotFound e) {
      throw new ResourceNotFoundException(Constants.USER_NOT_FOUND + userId);
    } catch (FeignException e) {
      throw new ServiceUnavailableException("User service is unavailable");
    }
  }
  /**
   * Retrieves restaurant details by its ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return RestaurantOutDTO containing restaurant details
   * @throws ResourceNotFoundException if the restaurant is not found
   */
  private RestaurantOutDTO getRestaurant(final Long restaurantId) {
    try {
      return restaurantFeignClient.getRestaurantById(restaurantId);
    } catch (FeignException.NotFound e) {
      throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND + restaurantId);
    } catch (FeignException e) {
      throw new ServiceUnavailableException("Restaurant service is unavailable");
    }
  }
  /**
   * Validates whether the order can be created based on user and restaurant data.
   *
   * @param user the user placing the order
   * @param restaurant the restaurant from where the order is placed
   * @param orderCreateDTO the DTO containing order creation details
   * @throws InvalidInputException if the restaurant is closed or insufficient wallet balance
   */
  private void validateOrderCreation(final UserResponseDTO user, final RestaurantOutDTO restaurant,
                                     final OrderCreateDTO orderCreateDTO) {
    if (user == null || restaurant == null) {
      throw new ResourceNotFoundException("User or Restaurant not found");
    }

    if (!restaurant.getIsOpen()) {
      throw new InvalidInputException(Constants.RESTAURANT_IS_CLOSED);
    }

    double totalAmount = calculateTotalAmount(orderCreateDTO.getOrderItems());
    if (user.getWalletBalance() < totalAmount) {
      throw new InvalidInputException(Constants.INSUFFICIENT_WALLET_BALANCE);
    }
  }
  /**
   * Calculates the total amount for an order based on its items.
   *
   * @param orderItems list of items in the order
   * @return the total amount for the order
   */
  private double calculateTotalAmount(final List<OrderItemDTO> orderItems) {
    return orderItems.stream()
      .mapToDouble(item -> item.getPrice() * item.getQuantity())
      .sum();
  }

  /**
   * Creates an order entity from the given details.
   *
   * @param orderCreateDTO the DTO containing order creation details
   * @param userId the ID of the user placing the order
   * @param restaurantId the ID of the restaurant where the order is placed
   * @return Order entity ready for persistence
   */
  private Order createOrderEntity(final OrderCreateDTO orderCreateDTO, final Long userId,
                                  final Long restaurantId) {
    Order order = new Order();
    order.setUserId(userId);
    order.setRestaurantId(restaurantId);
    order.setOrderStatus(OrderStatus.IN_CART);
    order.setPlacedAt(LocalDateTime.now());
    order.setUpdatedAt(LocalDateTime.now());
    order.setTotalAmount(calculateTotalAmount(orderCreateDTO.getOrderItems()));
    return order;
  }
  /**
   * Creates order item entities from the given DTOs.
   *
   * @param orderItemDTOs list of DTOs representing the items in the order
   * @param orderId the ID of the order
   * @return list of OrderItem entities ready for persistence
   */
  private List<OrderItem> createOrderItems(final List<OrderItemDTO> orderItemDTOs,
                                           final Long orderId) {
    return orderItemDTOs.stream()
      .map(itemDTO -> {
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setFoodItemId(itemDTO.getFoodItemId());
        item.setFoodItemName(itemDTO.getFoodItemName());
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(itemDTO.getPrice());
        return item;
      })
      .collect(Collectors.toList());
  }
  /**
   * Retrieves an order by its ID.
   *
   * @param orderId the ID of the order
   * @return ResponseEntity containing the order details
   * @throws ResourceNotFoundException if the order is not found
   */
  public ResponseEntity<OrderResponseDTO> getOrder(final Long orderId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));
    List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
    log.info("Retrieved order with ID: {}", orderId);
    return new ResponseEntity<>(createOrderResponseDTO(order, orderItems), HttpStatus.OK);
  }
  /**
   * Updates the status of an order based on its current status and new status provided.
   *
   * @param orderId the ID of the order
   * @param newStatus the new status to be applied to the order
   * @return ResponseEntity with updated order details
   * @throws ResourceNotFoundException if the order is not found
   * @throws InvalidInputException if status update is not valid
   */
  public ResponseEntity<?> updateOrderStatus(final Long orderId, final OrderStatus newStatus) {
    log.info("Inside update order status with ID:{}", orderId);
    try {
      Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));

      if (newStatus == OrderStatus.PLACED && order.getOrderStatus() == OrderStatus.IN_CART) {
        log.info("Processing order placement for ID:{}", orderId);
        UserResponseDTO user = userFeignClient.getUserById(order.getUserId());
        if (user.getWalletBalance() < order.getTotalAmount()) {
          log.warn("Insufficient balance for order ID:{}", orderId);
          throw new InvalidInputException(Constants.INSUFFICIENT_WALLET_BALANCE);
        }

        double newBalance = user.getWalletBalance() - order.getTotalAmount();
        ResponseEntity<UserResponseDTO> updateResponse = userFeignClient.updateWalletBalance(user.getUserId(), newBalance);

        if (updateResponse.getStatusCode() != HttpStatus.OK) {
          log.error("Failed to update wallet balance for user ID:{}", user.getUserId());
          throw new RuntimeException("Failed to update wallet balance");
        }

        order.setPlacedAt(LocalDateTime.now());
        order.setOrderStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        log.info("Updated order status to {} for order ID: {}", newStatus, orderId);
        return new ResponseEntity<>(createOrderResponseDTO(order, orderItemRepository.findByOrderId(orderId)), HttpStatus.OK);
      } else if (newStatus == OrderStatus.CANCELLED) {
        return cancelOrder(orderId);
      } else if (newStatus == OrderStatus.COMPLETED && order.getOrderStatus() == OrderStatus.PLACED) {
        order.setOrderStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        log.info("Updated order status to COMPLETED for order ID: {}", orderId);
        return new ResponseEntity<>(createOrderResponseDTO(order, orderItemRepository.findByOrderId(orderId)), HttpStatus.OK);
      }

      return new ResponseEntity<>(new MessageDTO("Invalid status update"), HttpStatus.BAD_REQUEST);
    } catch (ResourceNotFoundException | InvalidInputException e) {
      return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(new MessageDTO("An error occurred while processing the order"),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * Retrieves all orders placed by a specific user.
   *
   * @param userId the ID of the user
   * @return ResponseEntity containing the list of user orders
   */
  public ResponseEntity<?> getUserOrders(final Long userId) {
    List<Order> orders = orderRepository.findByUserId(userId);
    if (orders.isEmpty()) {
      log.info("No orders found for user ID: {}", userId);
      return new ResponseEntity<>(new MessageDTO("No orders found for this user"), HttpStatus.NOT_FOUND);
    }

    List<OrderResponseDTO> responseDTOs = orders.stream()
      .map(order -> {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        return createOrderResponseDTO(order, orderItems);
      })
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for user ID: {}", orders.size(), userId);
    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
  }
  /**
   * Retrieves all orders for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return ResponseEntity containing the list of restaurant orders
   */
  public ResponseEntity<?> getRestaurantOrders(final Long restaurantId) {
    List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
    if (orders.isEmpty()) {
      log.info("No orders found for restaurant ID: {}", restaurantId);
      return new ResponseEntity<>(new MessageDTO("No orders found for this restaurant"), HttpStatus.NOT_FOUND);
    }

    List<OrderResponseDTO> responseDTOs = orders.stream()
      .map(order -> {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        return createOrderResponseDTO(order, orderItems);
      })
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for restaurant ID: {}", orders.size(), restaurantId);
    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
  }

  /**
   * Cancels an order if the conditions are met, such as being within a specific time frame.
   *
   * @param orderId the ID of the order to be cancelled
   * @return ResponseEntity containing the cancellation confirmation
   * @throws InvalidInputException if the order cannot be cancelled
   * @throws ResourceNotFoundException if the order is not found
   */
  public ResponseEntity<MessageDTO> cancelOrder(final Long orderId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));

    if (order.getOrderStatus() != OrderStatus.PLACED) {
      throw new InvalidInputException("Only placed orders can be cancelled");
    }

    if (order.getPlacedAt() == null) {
      throw new InvalidInputException("Order has not been placed yet");
    }

    if (ChronoUnit.SECONDS.between(order.getPlacedAt(), LocalDateTime.now()) > 30) {
      log.warn("Attempt to cancel order {} after 30 seconds", orderId);
      throw new InvalidInputException(Constants.ORDER_CANCELLATION_TIME_EXCEEDED);
    }

    UserResponseDTO user = userFeignClient.getUserById(order.getUserId());
    double newBalance = user.getWalletBalance() + order.getTotalAmount();
    userFeignClient.updateWalletBalance(user.getUserId(), newBalance);

    order.setOrderStatus(OrderStatus.CANCELLED);
    order.setUpdatedAt(LocalDateTime.now());
    orderRepository.save(order);

    log.info("Cancelled order with ID: {}", orderId);
    return new ResponseEntity<>(new MessageDTO(Constants.ORDER_CANCELLED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Validates both the user and restaurant by their IDs to ensure both exist.
   *
   * @param userId the ID of the user
   * @param restaurantId the ID of the restaurant
   * @return true if both user and restaurant exist, false otherwise
   */
  boolean validateUserAndRestaurant(final Long userId, final Long restaurantId) {
    try {
      userFeignClient.getUserById(userId);
      restaurantFeignClient.getRestaurantById(restaurantId);
      return true;
    } catch (FeignException e) {
      log.error("Error validating user ID: {} or restaurant ID: {}", userId, restaurantId, e);
      return false;
    }
  }
  /**
   * Adds an item to an existing order if it is still in the cart status.
   *
   * @param orderId the ID of the order
   * @param addItemDTO the DTO containing details of the item to be added
   * @return ResponseEntity containing the updated order details
   * @throws InvalidInputException if the order is not in cart status or invalid item quantity
   */
  public ResponseEntity<OrderResponseDTO> addItemToOrder(final Long orderId, final OrderItemDTO addItemDTO) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));

    if (order.getOrderStatus() != OrderStatus.IN_CART) {
      throw new InvalidInputException(Constants.CANNOT_MODIFY_NON_CART_ORDER);
    }

    if (addItemDTO.getQuantity() <= 0) {
      throw new InvalidInputException("Item quantity must be greater than zero");
    }

    OrderItem newItem = new OrderItem();
    newItem.setOrderId(orderId);
    newItem.setFoodItemId(addItemDTO.getFoodItemId());
    newItem.setFoodItemName(addItemDTO.getFoodItemName());
    newItem.setQuantity(addItemDTO.getQuantity());
    newItem.setPrice(addItemDTO.getPrice());

    orderItemRepository.save(newItem);

    order.setTotalAmount(order.getTotalAmount() + (addItemDTO.getPrice() * addItemDTO.getQuantity()));
    order.setUpdatedAt(LocalDateTime.now());
    orderRepository.save(order);

    List<OrderItem> updatedItems = orderItemRepository.findByOrderId(orderId);
    log.info("Item added to order: {}", orderId);
    return new ResponseEntity<>(createOrderResponseDTO(order, updatedItems), HttpStatus.OK);
  }
  /**
   * Removes an item from an existing order if it is still in the cart status.
   *
   * @param orderId the ID of the order
   * @param foodItemId the ID of the food item to be removed
   * @return ResponseEntity containing the updated order details
   * @throws InvalidInputException if the order is not in cart status
   * @throws ResourceNotFoundException if the item is not found in the order
   */
  public ResponseEntity<OrderResponseDTO> removeItemFromOrder(final Long orderId, final Long foodItemId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));

    if (order.getOrderStatus() != OrderStatus.IN_CART) {
      throw new InvalidInputException(Constants.CANNOT_MODIFY_NON_CART_ORDER);
    }

    OrderItem itemToRemove = orderItemRepository.findByOrderIdAndFoodItemId(orderId, foodItemId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ITEM_NOT_FOUND_IN_ORDER));

    order.setTotalAmount(order.getTotalAmount() - (itemToRemove.getPrice() * itemToRemove.getQuantity()));
    order.setUpdatedAt(LocalDateTime.now());
    orderRepository.save(order);

    orderItemRepository.delete(itemToRemove);

    List<OrderItem> updatedItems = orderItemRepository.findByOrderId(orderId);
    log.info("Item removed from order: {}, foodItemId: {}", orderId, foodItemId);
    return new ResponseEntity<>(createOrderResponseDTO(order, updatedItems), HttpStatus.OK);
  }
  /**
   * Updates the quantity of an item in an existing order if it is still in the cart status.
   *
   * @param orderId the ID of the order
   * @param foodItemId the ID of the food item to be updated
   * @param updateQuantityDTO the DTO containing the new quantity
   * @return ResponseEntity containing the updated order details
   * @throws InvalidInputException if the order is not in cart status or quantity is invalid
   */
  public ResponseEntity<OrderResponseDTO> updateItemQuantity(final Long orderId, final Long foodItemId,
                                                             final UpdateItemQuantityDTO updateQuantityDTO) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));

    if (order.getOrderStatus() != OrderStatus.IN_CART) {
      throw new InvalidInputException(Constants.CANNOT_MODIFY_NON_CART_ORDER);
    }

    OrderItem itemToUpdate = orderItemRepository.findByOrderIdAndFoodItemId(orderId, foodItemId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ITEM_NOT_FOUND_IN_ORDER));

    if (updateQuantityDTO.getQuantity() < 0) {
      throw new InvalidInputException("Item quantity cannot be negative");
    }

    int quantityDifference = updateQuantityDTO.getQuantity() - itemToUpdate.getQuantity();
    double priceDifference = quantityDifference * itemToUpdate.getPrice();
    order.setTotalAmount(order.getTotalAmount() + priceDifference);
    order.setUpdatedAt(LocalDateTime.now());

    if (updateQuantityDTO.getQuantity() == 0) {
      orderItemRepository.delete(itemToUpdate);
      log.info("Item removed from order due to zero quantity: orderId={}, foodItemId={}", orderId, foodItemId);
    } else {
      itemToUpdate.setQuantity(updateQuantityDTO.getQuantity());
      orderItemRepository.save(itemToUpdate);
    }

    orderRepository.save(order);

    List<OrderItem> updatedItems = orderItemRepository.findByOrderId(orderId);
    log.info("Item quantity updated in order: {}, foodItemId: {}, new quantity: {}", orderId, foodItemId,
      updateQuantityDTO.getQuantity());
    return new ResponseEntity<>(createOrderResponseDTO(order, updatedItems), HttpStatus.OK);
  }
  /**
   * Deletes an order and its associated items if the order is in the 'IN_CART' status.
   *
   * @param orderId the ID of the order to be deleted
   * @return ResponseEntity containing a message indicating successful deletion
   * @throws ResourceNotFoundException if no order is found with the given ID
   * @throws InvalidInputException if the order status is not 'IN_CART'
   */
  @Transactional
  public ResponseEntity<MessageDTO> deleteOrder(final Long orderId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND + orderId));
    if (order.getOrderStatus() != OrderStatus.IN_CART) {
      throw new InvalidInputException("Only orders in cart can be deleted");
    }
    orderItemRepository.deleteByOrderId(orderId);
    orderRepository.delete(order);

    log.info("Deleted order with ID: {}", orderId);
    return new ResponseEntity<>(new MessageDTO("Order deleted successfully"), HttpStatus.OK);
  }
  /**
   * Creates a response DTO for the given order and its items.
   *
   * @param order the order entity
   * @param orderItems the list of items in the order
   * @return OrderResponseDTO containing order and item details
   */
  private OrderResponseDTO createOrderResponseDTO(final Order order, final List<OrderItem> orderItems) {
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    responseDTO.setOrderId(order.getOrderId());
    responseDTO.setUserId(order.getUserId());
    responseDTO.setRestaurantId(order.getRestaurantId());
    responseDTO.setOrderStatus(order.getOrderStatus());
    responseDTO.setPlacedAt(order.getPlacedAt());
    responseDTO.setUpdatedAt(order.getUpdatedAt());
    responseDTO.setTotalAmount(order.getTotalAmount());

    List<OrderItemDTO> orderItemDTOs = orderItems.stream()
      .map(this::convertToOrderItemDTO)
      .collect(Collectors.toList());
    responseDTO.setOrderItems(orderItemDTOs);

    return responseDTO;
  }
  /**
   * Converts an entity to DTO.
   * @param item for incoming data.
   * @return OrderItemDTO.
   */
  private OrderItemDTO convertToOrderItemDTO(final OrderItem item) {
    OrderItemDTO itemDTO = new OrderItemDTO();
    itemDTO.setFoodItemId(item.getFoodItemId());
    itemDTO.setFoodItemName(item.getFoodItemName());
    itemDTO.setQuantity(item.getQuantity());
    itemDTO.setPrice(item.getPrice());
    return itemDTO;
  }
}

