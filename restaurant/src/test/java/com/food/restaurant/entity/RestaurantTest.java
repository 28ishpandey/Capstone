package com.food.restaurant.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

  private Restaurant restaurant1;
  private Restaurant restaurant2;

  @BeforeEach
  void setUp() {
    restaurant1 = new Restaurant();
    restaurant1.setId(1L);
    restaurant1.setEmail("test1@gmail.com");
    restaurant1.setContactNumber("9876543210");
    restaurant1.setPassword("password123");
    restaurant1.setName("The Good Place");
    restaurant1.setAddress("123 Test St");
    restaurant1.setTimings("09:00-21:00");
    restaurant1.setImage(new byte[]{1, 2, 3});
    restaurant1.setIsOpen(true);
    restaurant1.setRestaurantOwnerId(1L);

    restaurant2 = new Restaurant();
    restaurant2.setId(1L);
    restaurant2.setEmail("test1@gmail.com");
    restaurant2.setContactNumber("9876543210");
    restaurant2.setPassword("password123");
    restaurant2.setName("The Good Place");
    restaurant2.setAddress("123 Test St");
    restaurant2.setTimings("09:00-21:00");
    restaurant2.setImage(new byte[]{1, 2, 3});
    restaurant2.setIsOpen(true);
    restaurant2.setRestaurantOwnerId(1L);
  }

  @Test
  void testId() {
    restaurant1.setId(2L);
    assertEquals(2L, restaurant1.getId());
  }

  @Test
  void testEmail() {
    restaurant1.setEmail("test2@gmail.com");
    assertEquals("test2@gmail.com", restaurant1.getEmail());
  }

  @Test
  void testContactNumber() {
    restaurant1.setContactNumber("9876543211");
    assertEquals("9876543211", restaurant1.getContactNumber());
  }

  @Test
  void testPassword() {
    restaurant1.setPassword("newpassword");
    assertEquals("newpassword", restaurant1.getPassword());
  }

  @Test
  void testName() {
    restaurant1.setName("The Better Place");
    assertEquals("The Better Place", restaurant1.getName());
  }

  @Test
  void testAddress() {
    restaurant1.setAddress("456 New St");
    assertEquals("456 New St", restaurant1.getAddress());
  }

  @Test
  void testTimings() {
    restaurant1.setTimings("10:00-22:00");
    assertEquals("10:00-22:00", restaurant1.getTimings());
  }

  @Test
  void testImage() {
    restaurant1.setImage(new byte[]{4, 5, 6});
    assertArrayEquals(new byte[]{4, 5, 6}, restaurant1.getImage());
  }

  @Test
  void testIsOpen() {
    restaurant1.setIsOpen(false);
    assertFalse(restaurant1.getIsOpen());
  }

  @Test
  void testRestaurantOwnerId() {
    restaurant1.setRestaurantOwnerId(2L);
    assertEquals(2L, restaurant1.getRestaurantOwnerId());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(restaurant1, restaurant2);
    assertEquals(restaurant1.hashCode(), restaurant2.hashCode());

    restaurant2.setName("A Different Place");
    assertNotEquals(restaurant1, restaurant2);
    assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());
  }

  @Test
  void testNotEqualsNull() {
    assertNotEquals(restaurant1, null);
  }

  @Test
  void testNotEqualsDifferentClass() {
    assertNotEquals(restaurant1, "A String");
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(restaurant1, restaurant1);
  }
}
