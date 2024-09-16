package com.food.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a category in a restaurant.
 * Contains details such as the restaurant ID and the category name.
 * This entity is mapped to a database table that stores these details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  /**
   * The unique identifier for the category.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The identifier of the restaurant this category is associated with.
   */
  @Column(nullable = false)
  private Long restaurantId;

  /**
   * The name of the category. Cannot be blank.
   */
  @NotBlank(message = "Category name is required")
  @Column(nullable = false)
  private String name;
}
