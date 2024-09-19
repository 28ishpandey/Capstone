package com.food.restaurant.controller;

import com.food.restaurant.dto.ForgotPasswordDTO;
import com.food.restaurant.dto.LoginDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.dto.RestaurantOwnerUpdateDTO;
import com.food.restaurant.service.RestaurantOwnerService;
import com.food.restaurant.service.RestaurantService;
import com.food.restaurant.util.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class responsible for handling restaurant owner-related operations.
 * This class provides endpoints for creating, updating, deleting, and retrieving restaurant owners,
 * as well as logging in and managing associated restaurants.
 * It also includes functionality for handling forgot password requests.
 */
@RestController
@RequestMapping("/restaurant-owners")
@Slf4j
public class RestaurantOwnerController {
  /**
   * Service for handling owner-related operations.
   */
  @Autowired
  private RestaurantOwnerService ownerService;
  /**
   * Service for handling restaurant-related operations.
   */
  @Autowired
  private RestaurantService restaurantService;

  /**
   * Endpoint for creating a new restaurant owner.
   *
   * @param ownerDto The details of the restaurant owner to be created.
   * @return ResponseEntity with a message indicating the success or failure of the operation.
   */
  @PostMapping
  public ResponseEntity<MessageDTO> createRestaurantOwner(@Validated @RequestBody final RestaurantOwnerInDTO ownerDto) {
    log.info("Received request to create restaurant owner with email: {}", ownerDto.getEmail());
    return ownerService.createRestaurantOwner(ownerDto);
  }

  /**
   * Endpoint for updating an existing restaurant owner by their ID.
   *
   * @param id       The ID of the restaurant owner to be updated.
   * @param ownerDto The updated details of the restaurant owner.
   * @return ResponseEntity with a message indicating the success or failure of the operation.
   */
  @PutMapping("/{id}")
  public ResponseEntity<MessageDTO> updateRestaurantOwner(@PathVariable final Long id,
                                                          @Validated @RequestBody final RestaurantOwnerUpdateDTO ownerDto) {
    log.info("Received request to update restaurant owner with id: {} and email: {}", id, ownerDto.getEmail());
    return ownerService.updateRestaurantOwner(id, ownerDto);
  }

  /**
   * Endpoint for deleting a restaurant owner by their ID.
   *
   * @param id The ID of the restaurant owner to be deleted.
   * @return ResponseEntity with a message indicating the success or failure of the operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDTO> deleteRestaurantOwner(@PathVariable final Long id) {
    log.info("Received request to delete restaurant owner with id: {}", id);
    return ownerService.deleteRestaurantOwner(id);
  }

  /**
   * Endpoint for retrieving a restaurant owner by their ID.
   *
   * @param id The ID of the restaurant owner to retrieve.
   * @return ResponseEntity with the restaurant owner's details.
   */
  @GetMapping("/{id}")
  public ResponseEntity<RestaurantOwnerOutDTO> getRestaurantOwner(@PathVariable final Long id) {
    log.info("Received request to get restaurant owner with id: {}", id);
    return ownerService.getRestaurantOwner(id);
  }

  /**
   * Endpoint for retrieving all restaurant owners.
   *
   * @return ResponseEntity containing a list of all restaurant owners.
   */
  @GetMapping
  public ResponseEntity<List<RestaurantOwnerOutDTO>> getAllRestaurantOwners() {
    log.info("Received request to get all restaurant owners");
    return ownerService.getAllRestaurantOwners();
  }

  /**
   * Endpoint for restaurant owner login.
   *
   * @param loginDto The login credentials (email and password).
   * @return ResponseEntity containing the logged-in restaurant owner's details.
   */
  @PostMapping("/login")
  public ResponseEntity<RestaurantOwnerOutDTO> login(@Validated @RequestBody final LoginDTO loginDto) {
    log.info("Received login request for email: {}", loginDto.getEmail());
    return ownerService.login(loginDto);
  }

  /**
   * Endpoint for retrieving all restaurants owned by a particular restaurant owner.
   *
   * @param ownerId The ID of the restaurant owner.
   * @return ResponseEntity containing a list of restaurants owned by the specified owner.
   */
  @GetMapping("/owner/{ownerId}")
  public ResponseEntity<List<RestaurantOutDTO>> getRestaurantsByOwnerId(@PathVariable final Long ownerId) {
    log.info("Received request to fetch all restaurants for owner ID: {}", ownerId);
    return ownerService.getRestaurantsByOwnerId(ownerId);
  }

  /**
   * Endpoint for handling forgot password requests.
   *
   * @param forgotPasswordDTO Contains the email of the restaurant owner who forgot their password.
   * @return ResponseEntity with a message indicating that the password reset email has been sent.
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<MessageDTO> forgotPassword(@RequestBody final ForgotPasswordDTO forgotPasswordDTO) {
    log.info("Forgot password request received for email: {}", forgotPasswordDTO.getEmail());
    ownerService.forgotPassword(forgotPasswordDTO);
    log.info("Forgot password email sent");
    return new ResponseEntity<>(new MessageDTO("Password reset email sent"), HttpStatus.OK);
  }
}
