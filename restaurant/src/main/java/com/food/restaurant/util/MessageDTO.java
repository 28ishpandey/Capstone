package com.food.restaurant.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
  /**
   * The message content.
   * <p>
   * This field contains the message string that is intended to be conveyed
   * in the response. It is used to provide information or status updates.
   * </p>
   */
  private String message;
}
