package com.food.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Entity representing a food item in a restaurant.
 * Contains details such as category ID, restaurant ID, name, price, availability, type, image, and description.
 * This entity is mapped to a database table that stores these details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

  /**
   * The unique identifier for the food item.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The identifier of the category this food item belongs to.
   */
  @Column(nullable = false)
  private Long categoryId;

  /**
   * The identifier of the restaurant this food item is associated with.
   */
  @Column(nullable = false)
  private Long restaurantId;

  /**
   * The name of the food item. Cannot be blank.
   */
  @NotBlank(message = "Food item name is required")
  @Column(nullable = false)
  private String name;

  /**
   * The price of the food item. Must be greater than zero.
   */
  @Positive(message = "Price must be greater than zero")
  @Column(nullable = false)
  private double price;

  /**
   * The availability status of the food item.
   */
  @Column(nullable = false)
  private boolean availability;

  /**
   * Indicates whether the food item is vegetarian.
   */
  @Column(nullable = false)
  private boolean isVeg;

  /**
   * The image of the food item stored in binary format.
   */
  @Lob
  private byte[] image;

  /**
   * A description of the food item.
   */
  private String description;
}
