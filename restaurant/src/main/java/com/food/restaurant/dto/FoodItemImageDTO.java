package com.food.restaurant.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object for updating the image of a food item.
 */
@Data
public class FoodItemImageDTO {

  /**
   * The unique identifier for the food item.
   */
  private Long foodItemId;

  /**
   * The image file of the food item to be uploaded.
   */
  private MultipartFile image;
}
