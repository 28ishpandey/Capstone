package com.food.user.usertests;

import com.food.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserResponseDTOTest {
  /**
   * Initial wallet balance for a new user in the test cases.
   */
  private static final double INITIAL_WALLET_BALANCE = 1000.0;
  @Test
  void testUserIdField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getUserId());

    Long userId = 1L;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());
  }

  @Test
  void testEmailField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getEmail());

    String email = "test@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());
  }

  @Test
  void testContactNumberField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getContactNumber());

    String contactNumber = "1234567890";
    dto.setContactNumber(contactNumber);
    assertEquals(contactNumber, dto.getContactNumber());
  }

  @Test
  void testFirstNameField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getFirstName());

    String firstName = "firstname";
    dto.setFirstName(firstName);
    assertEquals(firstName, dto.getFirstName());
  }

  @Test
  void testLastNameField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getLastName());

    String lastName = "lastname";
    dto.setLastName(lastName);
    assertEquals(lastName, dto.getLastName());
  }

  @Test
  void testWalletBalanceField() {
    UserResponseDTO dto = new UserResponseDTO();
    assertNull(dto.getWalletBalance());

    Double walletBalance = INITIAL_WALLET_BALANCE;
    dto.setWalletBalance(walletBalance);
    assertEquals(walletBalance, dto.getWalletBalance());
  }
}
