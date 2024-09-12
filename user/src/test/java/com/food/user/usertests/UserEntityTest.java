package com.food.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

  private Users user1;
  private Users user2;

  @BeforeEach
  void setUp() {
    user1 = new Users();
    user1.setUserId(1L);
    user1.setEmail("test1@gmail.com");
    user1.setContactNumber("9876543210");
    user1.setPassword("password123");
    user1.setFirstName("John");
    user1.setLastName("Doe");
    user1.setWalletBalance(1000.0);
    user1.setAddress("123 Test St");

    user2 = new Users();
    user2.setUserId(1L);
    user2.setEmail("test1@gmail.com");
    user2.setContactNumber("9876543210");
    user2.setPassword("password123");
    user2.setFirstName("John");
    user2.setLastName("Doe");
    user2.setWalletBalance(1000.0);
    user2.setAddress("123 Test St");
  }

  @Test
  void testUserId() {
    user1.setUserId(2L);
    assertEquals(2L, user1.getUserId());
  }

  @Test
  void testEmail() {
    user1.setEmail("test2@gmail.com");
    assertEquals("test2@gmail.com", user1.getEmail());
  }

  @Test
  void testContactNumber() {
    user1.setContactNumber("9876543211");
    assertEquals("9876543211", user1.getContactNumber());
  }

  @Test
  void testPassword() {
    user1.setPassword("newpassword");
    assertEquals("newpassword", user1.getPassword());
  }

  @Test
  void testFirstName() {
    user1.setFirstName("Jane");
    assertEquals("Jane", user1.getFirstName());
  }

  @Test
  void testLastName() {
    user1.setLastName("Smith");
    assertEquals("Smith", user1.getLastName());
  }

  @Test
  void testWalletBalance() {
    user1.setWalletBalance(2000.0);
    assertEquals(2000.0, user1.getWalletBalance());
  }

  @Test
  void testAddress() {
    user1.setAddress("456 New St");
    assertEquals("456 New St", user1.getAddress());
  }

  @Test
  void testEqualsAndHashCode() {
    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());

    user2.setEmail("different@gmail.com");
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());
  }

  @Test
  void testNotEqualsNull() {
    assertNotEquals(user1, null);
  }

  @Test
  void testNotEqualsDifferentClass() {
    assertNotEquals(user1, "A String");
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(user1, user1);
  }
}
