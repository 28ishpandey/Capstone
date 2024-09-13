package com.food.restaurant.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantDetailDTOTest {

  @Test
  void testRestaurantId() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getRestaurantId());

    dto.setRestaurantId(1L);
    assertEquals(1L, dto.getRestaurantId());
  }

  @Test
  void testRestaurantName() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getRestaurantName());

    dto.setRestaurantName("Test Restaurant");
    assertEquals("Test Restaurant", dto.getRestaurantName());
  }

  @Test
  void testEmail() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getEmail());

    dto.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", dto.getEmail());
  }

  @Test
  void testContactNumber() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getContactNumber());

    dto.setContactNumber("1234567890");
    assertEquals("1234567890", dto.getContactNumber());
  }

  @Test
  void testAddress() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getAddress());

    dto.setAddress("123 Test St");
    assertEquals("123 Test St", dto.getAddress());
  }

  @Test
  void testTimings() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getTimings());

    dto.setTimings("9AM-9PM");
    assertEquals("9AM-9PM", dto.getTimings());
  }

  @Test
  void testIsOpen() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getIsOpen());

    dto.setIsOpen(true);
    assertTrue(dto.getIsOpen());
  }

  @Test
  void testImage() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getImage());

    byte[] image = {1, 2, 3};
    dto.setImage(image);
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void testCategories() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    assertNull(dto.getCategories());

    List<CategoryDetailsDTO> categories = Arrays.asList(new CategoryDetailsDTO(), new CategoryDetailsDTO());
    dto.setCategories(categories);
    assertEquals(2, dto.getCategories().size());
    assertSame(categories, dto.getCategories());
  }

  @Test
  void testEqualsAndHashCode() {
    RestaurantDetailsDTO dto1 = new RestaurantDetailsDTO();
    dto1.setRestaurantId(1L);
    dto1.setRestaurantName("Test Restaurant");

    RestaurantDetailsDTO dto2 = new RestaurantDetailsDTO();
    dto2.setRestaurantId(1L);
    dto2.setRestaurantName("Test Restaurant");

    RestaurantDetailsDTO dto3 = new RestaurantDetailsDTO();
    dto3.setRestaurantId(2L);
    dto3.setRestaurantName("Another Restaurant");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
    dto.setRestaurantId(1L);
    dto.setRestaurantName("Test Restaurant");
    dto.setEmail("test@gmail.com");
    dto.setContactNumber("1234567890");
    dto.setAddress("123 Test St");
    dto.setTimings("9AM-9PM");
    dto.setIsOpen(true);
    dto.setImage(new byte[]{1, 2, 3});
    dto.setCategories(Arrays.asList(new CategoryDetailsDTO(), new CategoryDetailsDTO()));

    String toStringResult = dto.toString();
    assertTrue(toStringResult.contains("restaurantId=1"));
    assertTrue(toStringResult.contains("restaurantName=Test Restaurant"));
    assertTrue(toStringResult.contains("email=test@gmail.com"));
    assertTrue(toStringResult.contains("contactNumber=1234567890"));
    assertTrue(toStringResult.contains("address=123 Test St"));
    assertTrue(toStringResult.contains("timings=9AM-9PM"));
    assertTrue(toStringResult.contains("isOpen=true"));
    assertTrue(toStringResult.contains("image="));
    assertTrue(toStringResult.contains("categories="));
  }
}