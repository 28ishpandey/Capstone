package com.food.restaurant.service;

import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.entity.RestaurantOwner;
import com.food.restaurant.util.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantMapperTest {

  private RestaurantMapper restaurantMapper;

  @BeforeEach
  void setUp() {
    restaurantMapper = new RestaurantMapper();
  }

  @Test
  void testToDto_RestaurantOwner() {
    RestaurantOwner owner = new RestaurantOwner();
    owner.setId(1L);
    owner.setFirstName("first");
    owner.setLastName("last");
    owner.setEmail("test@example.com");
    owner.setContactNumber("1234567890");

    RestaurantOwnerOutDTO dto = restaurantMapper.toDto(owner);

    assertEquals(1L, dto.getId());
    assertEquals("first", dto.getFirstName());
    assertEquals("last", dto.getLastName());
    assertEquals("test@example.com", dto.getEmail());
    assertEquals("1234567890", dto.getContactNumber());
  }

  @Test
  void testToEntity_RestaurantOwner() {
    RestaurantOwnerInDTO dto = new RestaurantOwnerInDTO();
    dto.setFirstName("firstname");
    dto.setLastName("lastname");
    dto.setEmail("test@example.com");
    dto.setContactNumber("9876543210");
    dto.setPassword("password123");

    RestaurantOwner owner = restaurantMapper.toEntity(dto);

    assertEquals("firstname", owner.getFirstName());
    assertEquals("lastname", owner.getLastName());
    assertEquals("test@example.com", owner.getEmail());
    assertEquals("9876543210", owner.getContactNumber());
    assertEquals("password123", owner.getPassword());
  }

  @Test
  void testToDto_Restaurant() {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1L);
    restaurant.setEmail("test@example.com");
    restaurant.setContactNumber("1234567890");
    restaurant.setName("Test Restaurant");
    restaurant.setAddress("123 Test St");
    restaurant.setTimings("9AM-9PM");
    restaurant.setIsOpen(true);
    byte[] image = {1, 2, 3};
    restaurant.setImage(image);

    RestaurantOutDTO dto = restaurantMapper.toDto(restaurant);

    assertEquals(1L, dto.getId());
    assertEquals("test@example.com", dto.getEmail());
    assertEquals("1234567890", dto.getContactNumber());
    assertEquals("Test Restaurant", dto.getName());
    assertEquals("123 Test St", dto.getAddress());
    assertEquals("9AM-9PM", dto.getTimings());
    assertTrue(dto.getIsOpen());
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void testToEntity_Restaurant() {
    RestaurantInDTO dto = new RestaurantInDTO();
    dto.setEmail("newtest@example.com");
    dto.setContactNumber("9876543210");
    dto.setPassword("password123");
    dto.setName("New Restaurant");
    dto.setAddress("456 New St");
    dto.setTimings("10AM-10PM");
    dto.setIsOpen(false);
    dto.setRestaurantOwnerId(2L);

    Restaurant restaurant = restaurantMapper.toEntity(dto);

    assertEquals("newtest@example.com", restaurant.getEmail());
    assertEquals("9876543210", restaurant.getContactNumber());
    assertEquals("password123", restaurant.getPassword());
    assertEquals("New Restaurant", restaurant.getName());
    assertEquals("456 New St", restaurant.getAddress());
    assertEquals("10AM-10PM", restaurant.getTimings());
    assertFalse(restaurant.getIsOpen());
    assertEquals(2L, restaurant.getRestaurantOwnerId());
  }
}