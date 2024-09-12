package com.food.restaurant.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodItemTest {

  private FoodItem foodItem1;
  private FoodItem foodItem2;

  @BeforeEach
  void setUp() {
    foodItem1 = new FoodItem();
    foodItem1.setId(1L);
    foodItem1.setCategoryId(100L);
    foodItem1.setRestaurantId(10L);
    foodItem1.setName("Pizza");
    foodItem1.setPrice(9.99);
    foodItem1.setAvailability(true);
    foodItem1.setVeg(false);
    foodItem1.setDescription("Delicious cheese pizza");
    foodItem1.setImage(new byte[]{1, 2, 3});

    foodItem2 = new FoodItem();
    foodItem2.setId(1L);
    foodItem2.setCategoryId(100L);
    foodItem2.setRestaurantId(10L);
    foodItem2.setName("Pizza");
    foodItem2.setPrice(9.99);
    foodItem2.setAvailability(true);
    foodItem2.setVeg(false);
    foodItem2.setDescription("Delicious cheese pizza");
    foodItem2.setImage(new byte[]{1, 2, 3});
  }

  @Test
  void testId() {
    foodItem1.setId(2L);
    assertEquals(2L, foodItem1.getId());
  }

  @Test
  void testCategoryId() {
    foodItem1.setCategoryId(200L);
    assertEquals(200L, foodItem1.getCategoryId());
  }

  @Test
  void testRestaurantId() {
    foodItem1.setRestaurantId(20L);
    assertEquals(20L, foodItem1.getRestaurantId());
  }

  @Test
  void testName() {
    foodItem1.setName("Burger");
    assertEquals("Burger", foodItem1.getName());
  }

  @Test
  void testPrice() {
    foodItem1.setPrice(12.99);
    assertEquals(12.99, foodItem1.getPrice());
  }

  @Test
  void testAvailability() {
    foodItem1.setAvailability(false);
    assertFalse(foodItem1.isAvailability());
  }

  @Test
  void testVeg() {
    foodItem1.setVeg(true);
    assertTrue(foodItem1.isVeg());
  }

  @Test
  void testDescription() {
    foodItem1.setDescription("Spicy chicken pizza");
    assertEquals("Spicy chicken pizza", foodItem1.getDescription());
  }

  @Test
  void testImage() {
    foodItem1.setImage(new byte[]{4, 5, 6});
    assertArrayEquals(new byte[]{4, 5, 6}, foodItem1.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(foodItem1, foodItem2);
    assertEquals(foodItem1.hashCode(), foodItem2.hashCode());

    foodItem2.setName("Pasta");
    assertNotEquals(foodItem1, foodItem2);
    assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());
  }

  @Test
  void testNotEqualsNull() {
    assertNotEquals(foodItem1, null);
  }

  @Test
  void testNotEqualsDifferentClass() {
    assertNotEquals(foodItem1, "A String");
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(foodItem1, foodItem1);
  }
}
