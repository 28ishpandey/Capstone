package com.food.user.usertests;

import com.food.user.dto.ForgotPasswordDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ForgotPasswordDTOTest {

  @Test
  void testEmailField() {
    ForgotPasswordDTO dto = new ForgotPasswordDTO();
    assertNull(dto.getEmail());

    String email = "test@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());
  }
}
