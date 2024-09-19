package com.food.user.usertests;

import com.food.user.dto.LoginDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoginDTOTest {

  @Test
  void testEmailField() {
    LoginDTO dto = new LoginDTO();
    assertNull(dto.getEmail());

    String email = "test@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());
  }

  @Test
  void testPasswordField() {
    LoginDTO dto = new LoginDTO();
    assertNull(dto.getPassword());

    String password = "securePassword123";
    dto.setPassword(password);
    assertEquals(password, dto.getPassword());
  }
}
