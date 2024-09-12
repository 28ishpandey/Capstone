package com.food.restaurant.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

  private Category category1;
  private Category category2;

  @BeforeEach
  void setUp() {
    category1 = new Category();
    category1.setId(1L);
    category1.setRestaurantId(10L);
    category1.setName("Desserts");

    category2 = new Category();
    category2.setId(1L);
    category2.setRestaurantId(10L);
    category2.setName("Desserts");
  }

  @Test
  void testId() {
    category1.setId(2L);
    assertEquals(2L, category1.getId());
  }

  @Test
  void testRestaurantId() {
    category1.setRestaurantId(20L);
    assertEquals(20L, category1.getRestaurantId());
  }

  @Test
  void testName() {
    category1.setName("Appetizers");
    assertEquals("Appetizers", category1.getName());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(category1, category2);
    assertEquals(category1.hashCode(), category2.hashCode());

    category2.setName("Main Course");
    assertNotEquals(category1, category2);
    assertNotEquals(category1.hashCode(), category2.hashCode());
  }

  @Test
  void testNotEqualsNull() {
    assertNotEquals(category1, null);
  }

  @Test
  void testNotEqualsDifferentClass() {
    assertNotEquals(category1, "A String");
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(category1, category1);
  }
}
