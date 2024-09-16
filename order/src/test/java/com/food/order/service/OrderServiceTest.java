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
import com.food.order.feign.RestaurantFeignClient;
import com.food.order.feign.UserFeignClient;
import com.food.order.repository.OrderItemRepository;
import com.food.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderItemRepository orderItemRepository;

  @Mock
  private UserFeignClient userFeignClient;

  @Mock
  private RestaurantFeignClient restaurantFeignClient;

  @InjectMocks
  private OrderService orderService;

  private OrderCreateDTO orderCreateDTO;
  private UserResponseDTO userResponseDTO;
  private RestaurantOutDTO restaurantOutDTO;
  private Order order;
  private OrderItem orderItem;

  @BeforeEach
  void setUp() {
    orderCreateDTO = new OrderCreateDTO();
    orderCreateDTO.setUserId(1L);
    orderCreateDTO.setRestaurantId(1L);
    OrderItemDTO orderItemDTO = new OrderItemDTO();
    orderItemDTO.setFoodItemId(1L);
    orderItemDTO.setFoodItemName("Pizza");
    orderItemDTO.setQuantity(2);
    orderItemDTO.setPrice(10.0);
    orderCreateDTO.setOrderItems(Collections.singletonList(orderItemDTO));

    userResponseDTO = new UserResponseDTO();
    userResponseDTO.setUserId(1L);
    userResponseDTO.setWalletBalance(100.0);

    restaurantOutDTO = new RestaurantOutDTO();
    restaurantOutDTO.setId(1L);
    restaurantOutDTO.setIsOpen(true);

    order = new Order();
    order.setOrderId(1L);
    order.setUserId(1L);
    order.setRestaurantId(1L);
    order.setOrderStatus(OrderStatus.IN_CART);
    order.setTotalAmount(20.0);
    order.setPlacedAt(LocalDateTime.now());
    order.setUpdatedAt(LocalDateTime.now());

    orderItem = new OrderItem();
    orderItem.setOrderItemId(1L);
    orderItem.setOrderId(1L);
    orderItem.setFoodItemId(1L);
    orderItem.setFoodItemName("Pizza");
    orderItem.setQuantity(2);
    orderItem.setPrice(10.0);
  }

  @Test
  void createOrder_Success() {
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(restaurantOutDTO);
    when(orderRepository.save(any(Order.class))).thenReturn(order);
    when(orderItemRepository.saveAll(anyList())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<OrderResponseDTO> response = orderService.createOrder(orderCreateDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getOrderId());
  }

  @Test
  void createOrder_UserNotFound() {
    when(userFeignClient.getUserById(anyLong())).thenReturn(null);

    assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderCreateDTO));
  }

  @Test
  void createOrder_RestaurantNotFound() {
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(null);

    assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderCreateDTO));
  }

  @Test
  void createOrder_RestaurantClosed() {
    restaurantOutDTO.setIsOpen(false);
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(restaurantOutDTO);

    assertThrows(InvalidInputException.class, () -> orderService.createOrder(orderCreateDTO));
  }

  @Test
  void createOrder_InsufficientBalance() {
    userResponseDTO.setWalletBalance(10.0);
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(restaurantOutDTO);

    assertThrows(InvalidInputException.class, () -> orderService.createOrder(orderCreateDTO));
  }

  @Test
  void getOrder_Success() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<OrderResponseDTO> response = orderService.getOrder(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getOrderId());
  }

  @Test
  void getOrder_NotFound() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(1L));
  }

  @Test
  void updateOrderStatus_PlaceOrder_Success() {
    order.setOrderStatus(OrderStatus.IN_CART);
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(userFeignClient.updateWalletBalance(anyLong(), anyDouble())).thenReturn(new ResponseEntity<>(userResponseDTO, HttpStatus.OK));
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<?> response = orderService.updateOrderStatus(1L, OrderStatus.PLACED);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof OrderResponseDTO);
    assertEquals(OrderStatus.PLACED, ((OrderResponseDTO) response.getBody()).getOrderStatus());
  }

  @Test
  void updateOrderStatus_PlaceOrder_InsufficientBalance() {
    order.setOrderStatus(OrderStatus.IN_CART);
    userResponseDTO.setWalletBalance(10.0);
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);

    ResponseEntity<?> response = orderService.updateOrderStatus(1L, OrderStatus.PLACED);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof MessageDTO);
    assertEquals(Constants.INSUFFICIENT_WALLET_BALANCE, ((MessageDTO) response.getBody()).getMessage());
  }

  @Test
  void updateOrderStatus_CancelOrder_Success() {
    order.setOrderStatus(OrderStatus.PLACED);
    order.setPlacedAt(LocalDateTime.now().minusSeconds(20));
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(userFeignClient.updateWalletBalance(anyLong(), anyDouble())).thenReturn(new ResponseEntity<>(userResponseDTO, HttpStatus.OK));

    ResponseEntity<?> response = orderService.updateOrderStatus(1L, OrderStatus.CANCELLED);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof MessageDTO);
    assertEquals(Constants.ORDER_CANCELLED_SUCCESSFULLY, ((MessageDTO) response.getBody()).getMessage());
  }

  @Test
  void updateOrderStatus_CancelOrder_TimeLimitExceeded() {
    order.setOrderStatus(OrderStatus.PLACED);
    order.setPlacedAt(LocalDateTime.now().minusSeconds(31));
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    ResponseEntity<?> response = orderService.updateOrderStatus(1L, OrderStatus.CANCELLED);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof MessageDTO);
    assertEquals(Constants.ORDER_CANCELLATION_TIME_EXCEEDED, ((MessageDTO) response.getBody()).getMessage());
  }

  @Test
  void updateOrderStatus_CompleteOrder_Success() {
    order.setOrderStatus(OrderStatus.PLACED);
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<?> response = orderService.updateOrderStatus(1L, OrderStatus.COMPLETED);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof OrderResponseDTO);
    assertEquals(OrderStatus.COMPLETED, ((OrderResponseDTO) response.getBody()).getOrderStatus());
  }

  @Test
  void getUserOrders_Success() {
    List<Order> orders = Arrays.asList(order);
    when(orderRepository.findByUserId(anyLong())).thenReturn(orders);
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<?> response = orderService.getUserOrders(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof List);
    assertEquals(1, ((List<?>) response.getBody()).size());
  }

  @Test
  void getUserOrders_NoOrdersFound() {
    when(orderRepository.findByUserId(anyLong())).thenReturn(Collections.emptyList());

    ResponseEntity<?> response = orderService.getUserOrders(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertTrue(response.getBody() instanceof MessageDTO);
  }

  @Test
  void getRestaurantOrders_Success() {
    List<Order> orders = Arrays.asList(order);
    when(orderRepository.findByRestaurantId(anyLong())).thenReturn(orders);
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    ResponseEntity<?> response = orderService.getRestaurantOrders(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof List);
    assertEquals(1, ((List<?>) response.getBody()).size());
  }

  @Test
  void getRestaurantOrders_NoOrdersFound() {
    when(orderRepository.findByRestaurantId(anyLong())).thenReturn(Collections.emptyList());

    ResponseEntity<?> response = orderService.getRestaurantOrders(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertTrue(response.getBody() instanceof MessageDTO);
  }

  @Test
  void addItemToOrder_Success() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));

    OrderItemDTO newItemDTO = new OrderItemDTO();
    newItemDTO.setFoodItemId(2L);
    newItemDTO.setFoodItemName("Burger");
    newItemDTO.setQuantity(1);
    newItemDTO.setPrice(15.0);

    ResponseEntity<OrderResponseDTO> response = orderService.addItemToOrder(1L, newItemDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(35.0, response.getBody().getTotalAmount());
  }

  @Test
  void addItemToOrder_OrderNotInCart() {
    order.setOrderStatus(OrderStatus.PLACED);
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    OrderItemDTO newItemDTO = new OrderItemDTO();
    newItemDTO.setFoodItemId(2L);
    newItemDTO.setQuantity(1);
    newItemDTO.setPrice(15.0);

    assertThrows(InvalidInputException.class, () -> orderService.addItemToOrder(1L, newItemDTO));
  }

  @Test
  void removeItemFromOrder_Success() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderIdAndFoodItemId(anyLong(), anyLong())).thenReturn(Optional.of(orderItem));
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.emptyList());

    ResponseEntity<OrderResponseDTO> response = orderService.removeItemFromOrder(1L, 1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(0.0, response.getBody().getTotalAmount());
    assertTrue(response.getBody().getOrderItems().isEmpty());
  }

  @Test
  void removeItemFromOrder_ItemNotFound() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderIdAndFoodItemId(anyLong(), anyLong())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> orderService.removeItemFromOrder(1L, 1L));
  }

  @Test
  void updateItemQuantity_Success() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderIdAndFoodItemId(anyLong(), anyLong())).thenReturn(Optional.of(orderItem));
    when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));
    UpdateItemQuantityDTO updateQuantityDTO = new UpdateItemQuantityDTO();
    updateQuantityDTO.setQuantity(3);

    ResponseEntity<OrderResponseDTO> response = orderService.updateItemQuantity(1L, 1L, updateQuantityDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(30.0, response.getBody().getTotalAmount());
    assertEquals(3, response.getBody().getOrderItems().get(0).getQuantity());
  }

  @Test
  void updateItemQuantity_RemoveItem() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderIdAndFoodItemId(anyLong(), anyLong())).thenReturn(Optional.of(orderItem));
    when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.emptyList());

    UpdateItemQuantityDTO updateQuantityDTO = new UpdateItemQuantityDTO();
    updateQuantityDTO.setQuantity(0);

    ResponseEntity<OrderResponseDTO> response = orderService.updateItemQuantity(1L, 1L, updateQuantityDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(0.0, response.getBody().getTotalAmount());
    assertTrue(response.getBody().getOrderItems().isEmpty());
    verify(orderItemRepository).delete(any(OrderItem.class));
  }

  @Test
  void updateItemQuantity_NegativeQuantity() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderItemRepository.findByOrderIdAndFoodItemId(anyLong(), anyLong())).thenReturn(Optional.of(orderItem));

    UpdateItemQuantityDTO updateQuantityDTO = new UpdateItemQuantityDTO();
    updateQuantityDTO.setQuantity(-1);

    assertThrows(InvalidInputException.class, () -> orderService.updateItemQuantity(1L, 1L, updateQuantityDTO));
  }

  @Test
  void cancelOrder_Success() {
    order.setOrderStatus(OrderStatus.PLACED);
    order.setPlacedAt(LocalDateTime.now().minusSeconds(20));
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(userFeignClient.updateWalletBalance(anyLong(), anyDouble())).thenReturn(new ResponseEntity<>(userResponseDTO, HttpStatus.OK));

    ResponseEntity<MessageDTO> response = orderService.cancelOrder(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constants.ORDER_CANCELLED_SUCCESSFULLY, response.getBody().getMessage());
    verify(orderRepository).save(argThat(savedOrder -> savedOrder.getOrderStatus() == OrderStatus.CANCELLED));
  }

  @Test
  void cancelOrder_NotPlaced() {
    order.setOrderStatus(OrderStatus.IN_CART);
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    assertThrows(InvalidInputException.class, () -> orderService.cancelOrder(1L));
  }

  @Test
  void cancelOrder_TimeLimitExceeded() {
    order.setOrderStatus(OrderStatus.PLACED);
    order.setPlacedAt(LocalDateTime.now().minusSeconds(31));
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    assertThrows(InvalidInputException.class, () -> orderService.cancelOrder(1L));
  }

  @Test
  void validateUserAndRestaurant_Success() {
    when(userFeignClient.getUserById(anyLong())).thenReturn(userResponseDTO);
    when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(restaurantOutDTO);

    assertTrue(orderService.validateUserAndRestaurant(1L, 1L));
  }

}