package com.food.restaurant.util;

import com.food.restaurant.dto.RestaurantInDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.entity.RestaurantOwner;
import org.springframework.stereotype.Component;

/**
 * A utility class for mapping between DTOs and entity objects related to restaurants
 * and restaurant owners.
 * <p>
 * This class provides methods to convert between entity objects and their corresponding
 * Data Transfer Objects (DTOs). It facilitates the transformation of data between different
 * layers of the application, such as converting database entities to DTOs for use in the API
 * layer and vice versa.
 * </p>
 */
@Component
public class RestaurantMapper {

  /**
   * Converts a {@link RestaurantOwner} entity to a {@link RestaurantOwnerOutDTO}.
   * <p>
   * This method maps the properties of the {@link RestaurantOwner} entity to a
   * {@link RestaurantOwnerOutDTO} for use in responses or other layers where a DTO is needed.
   * </p>
   *
   * @param owner the {@link RestaurantOwner} entity to be converted
   * @return the corresponding {@link RestaurantOwnerOutDTO} object
   */
  public RestaurantOwnerOutDTO toDto(final RestaurantOwner owner) {
    RestaurantOwnerOutDTO ownerDto = new RestaurantOwnerOutDTO();
    ownerDto.setId(owner.getId());
    ownerDto.setFirstName(owner.getFirstName());
    ownerDto.setLastName(owner.getLastName());
    ownerDto.setEmail(owner.getEmail());
    ownerDto.setContactNumber(owner.getContactNumber());
    return ownerDto;
  }

  /**
   * Converts a {@link RestaurantOwnerInDTO} to a {@link RestaurantOwner} entity.
   * <p>
   * This method maps the properties of the {@link RestaurantOwnerInDTO} to a
   * {@link RestaurantOwner} entity for storage in the database or other persistence layers.
   * </p>
   *
   * @param ownerDto the {@link RestaurantOwnerInDTO} to be converted
   * @return the corresponding {@link RestaurantOwner} entity
   */
  public RestaurantOwner toEntity(final RestaurantOwnerInDTO ownerDto) {
    RestaurantOwner owner = new RestaurantOwner();
    owner.setFirstName(ownerDto.getFirstName());
    owner.setLastName(ownerDto.getLastName());
    owner.setEmail(ownerDto.getEmail());
    owner.setContactNumber(ownerDto.getContactNumber());
    owner.setPassword(ownerDto.getPassword());
    return owner;
  }

  /**
   * Converts a {@link Restaurant} entity to a {@link RestaurantOutDTO}.
   * <p>
   * This method maps the properties of the {@link Restaurant} entity to a
   * {@link RestaurantOutDTO} for use in responses or other layers where a DTO is needed.
   * </p>
   *
   * @param restaurant the {@link Restaurant} entity to be converted
   * @return the corresponding {@link RestaurantOutDTO} object
   */
  public RestaurantOutDTO toDto(final Restaurant restaurant) {
    RestaurantOutDTO restaurantDto = new RestaurantOutDTO();
    restaurantDto.setId(restaurant.getId());
    restaurantDto.setEmail(restaurant.getEmail());
    restaurantDto.setContactNumber(restaurant.getContactNumber());
    restaurantDto.setName(restaurant.getName());
    restaurantDto.setAddress(restaurant.getAddress());
    restaurantDto.setTimings(restaurant.getTimings());
    restaurantDto.setIsOpen(restaurant.getIsOpen());
    restaurantDto.setImage(restaurant.getImage());
    return restaurantDto;
  }

  /**
   * Converts a {@link RestaurantInDTO} to a {@link Restaurant} entity.
   * <p>
   * This method maps the properties of the {@link RestaurantInDTO} to a
   * {@link Restaurant} entity for storage in the database or other persistence layers.
   * </p>
   *
   * @param restaurantDto the {@link RestaurantInDTO} to be converted
   * @return the corresponding {@link Restaurant} entity
   */
  public Restaurant toEntity(final RestaurantInDTO restaurantDto) {
    Restaurant restaurant = new Restaurant();
    restaurant.setEmail(restaurantDto.getEmail());
    restaurant.setContactNumber(restaurantDto.getContactNumber());
    restaurant.setPassword(restaurantDto.getPassword());
    restaurant.setName(restaurantDto.getName());
    restaurant.setAddress(restaurantDto.getAddress());
    restaurant.setTimings(restaurantDto.getTimings());
    restaurant.setIsOpen(restaurantDto.getIsOpen());
    restaurant.setRestaurantOwnerId(restaurantDto.getRestaurantOwnerId());
    return restaurant;
  }
}
