package com.food.restaurant.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object for updating the image of a restaurant.
 */
@Data
public class RestaurantImageDTO {

  /**
   * The unique identifier for the restaurant.
   */
  private Long restaurantId;

  /**
   * The image file of the restaurant to be uploaded.
   */
  private MultipartFile image;
}
