package com.food.order.controller;

import com.food.order.dto.MessageDTO;
import com.food.order.dto.OrderCreateDTO;
import com.food.order.dto.OrderItemDTO;
import com.food.order.dto.OrderResponseDTO;
import com.food.order.dto.UpdateItemQuantityDTO;
import com.food.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {

  @Mock
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createOrder_Success() {
    OrderCreateDTO createDTO = new OrderCreateDTO();
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    when(orderService.createOrder(any(OrderCreateDTO.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(responseDTO));

    ResponseEntity<OrderResponseDTO> response = orderController.createOrder(createDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(orderService).createOrder(createDTO);
  }

  @Test
  void getOrder_Success() {
    Long orderId = 1L;
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    when(orderService.getOrder(orderId)).thenReturn(ResponseEntity.ok(responseDTO));

    ResponseEntity<OrderResponseDTO> response = orderController.getOrder(orderId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(orderService).getOrder(orderId);
  }

  @Test
  void cancelOrder_Success() {
    Long orderId = 1L;
    MessageDTO messageDTO = new MessageDTO("Order cancelled successfully");
    when(orderService.cancelOrder(orderId)).thenReturn(ResponseEntity.ok(messageDTO));

    ResponseEntity<MessageDTO> response = orderController.cancelOrder(orderId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Order cancelled successfully", response.getBody().getMessage());
    verify(orderService).cancelOrder(orderId);
  }

  @Test
  void addItemToOrder_Success() {
    Long orderId = 1L;
    OrderItemDTO itemDTO = new OrderItemDTO();
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    when(orderService.addItemToOrder(eq(orderId), any(OrderItemDTO.class)))
      .thenReturn(ResponseEntity.ok(responseDTO));

    ResponseEntity<OrderResponseDTO> response = orderController.addItemToOrder(orderId, itemDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(orderService).addItemToOrder(orderId, itemDTO);
  }

  @Test
  void removeItemFromOrder_Success() {
    Long orderId = 1L;
    Long foodItemId = 2L;
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    when(orderService.removeItemFromOrder(orderId, foodItemId))
      .thenReturn(ResponseEntity.ok(responseDTO));

    ResponseEntity<OrderResponseDTO> response = orderController.removeItemFromOrder(orderId, foodItemId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(orderService).removeItemFromOrder(orderId, foodItemId);
  }

  @Test
  void updateItemQuantity_Success() {
    Long orderId = 1L;
    Long foodItemId = 2L;
    UpdateItemQuantityDTO updateDTO = new UpdateItemQuantityDTO();
    updateDTO.setQuantity(3);
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    when(orderService.updateItemQuantity(eq(orderId), eq(foodItemId), any(UpdateItemQuantityDTO.class)))
      .thenReturn(ResponseEntity.ok(responseDTO));

    ResponseEntity<OrderResponseDTO> response = orderController.updateItemQuantity(orderId, foodItemId, updateDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(orderService).updateItemQuantity(orderId, foodItemId, updateDTO);
  }
}