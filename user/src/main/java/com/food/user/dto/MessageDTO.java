package com.food.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transferring a message.
 * <p>
 * This class uses Lombok annotations to automatically generate getter, setter,
 * toString, equals, and hashCode methods, as well as no-argument and all-arguments
 * constructors.
 * </p>
 *
 * @see lombok.Data
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

  /**
   * The message content.
   */
  private String message;
}