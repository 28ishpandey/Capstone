package com.food.user.usertests;

import com.food.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
/**
 * Unit test for the Users entity class.
 */
class UserEntityTest {

  /**
   * The first user instance used for testing.
   */
  private Users user1;

  /**
   * The second user instance used for testing.
   */
  private Users user2;

  /**
   * Initial wallet balance for a new user in the test cases.
   */
  private static final double INITIAL_WALLET_BALANCE = 1000.0;

  /**
   * Updated wallet balance for a user after performing a test update.
   */
  private static final double UPDATED_WALLET_BALANCE = 2000.0;

  @BeforeEach
  void setUp() {
    user1 = new Users();
    user1.setUserId(1L);
    user1.setEmail("test1@gmail.com");
    user1.setContactNumber("9876543210");
    user1.setPassword("password123");
    user1.setFirstName("firstname");
    user1.setLastName("lastname");
    user1.setWalletBalance(INITIAL_WALLET_BALANCE);
    user1.setAddress("address");

    user2 = new Users();
    user2.setUserId(1L);
    user2.setEmail("test1@gmail.com");
    user2.setContactNumber("9876543210");
    user2.setPassword("password123");
    user2.setFirstName("firstname");
    user2.setLastName("lastname");
    user2.setWalletBalance(INITIAL_WALLET_BALANCE);
    user2.setAddress("address");
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
    user1.setWalletBalance(UPDATED_WALLET_BALANCE);
    assertEquals(UPDATED_WALLET_BALANCE, user1.getWalletBalance());
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
