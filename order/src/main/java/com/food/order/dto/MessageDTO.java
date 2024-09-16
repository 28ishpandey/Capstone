package com.food.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) to encapsulate a response message.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
  /**
   * The message to be communicated in the response.
   */
  private String message;
}
