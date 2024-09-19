package com.food.user.dto;

import lombok.Data;
/**
 * Data Transfer Object (DTO) for handling 'Contact Us' requests.
 * This class encapsulates the details provided by the user, including
 * their email, the subject of the message, and the message content.
 */
@Data
public class ContactUsDTO {

  /**
   * The email of the user who is sending the message.
   */
  private String email;

  /**
   * The subject of the message.
   */
  private String subject;

  /**
   * The content of the message sent by the user.
   */
  private String message;
}
