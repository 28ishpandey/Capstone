package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.ForgotPasswordDTO;
import com.food.restaurant.dto.LoginDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.dto.RestaurantOwnerUpdateDTO;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.entity.RestaurantOwner;
import com.food.restaurant.exception.ResourceAlreadyExistException;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.repository.RestaurantOwnerRepository;
import com.food.restaurant.repository.RestaurantRepository;
import com.food.restaurant.util.MessageDTO;
import com.food.restaurant.util.PasswordUtil;
import com.food.restaurant.util.RestaurantMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing restaurant owners.
 * Provides functionality to create, update, delete, and retrieve restaurant owner information.
 * Also supports login, password reset, and retrieving restaurants associated with an owner.
 * Uses SLF4J for logging and JavaMailSender for sending emails.
 */
@Service
@Slf4j
public class RestaurantOwnerService {

  @Autowired
  private RestaurantOwnerRepository ownerRepository;

  @Autowired
  private RestaurantMapper restaurantMapper;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private JavaMailSender javaMailSender;

  /**
   * Creates a new restaurant owner.
   * Checks if the email already exists and throws an exception if it does.
   * Encrypts the owner's password before saving.
   *
   * @param ownerDto the DTO containing restaurant owner information to be created
   * @return ResponseEntity containing a message DTO and HTTP status code
   */
  public ResponseEntity<MessageDTO> createRestaurantOwner(RestaurantOwnerInDTO ownerDto) {
    log.info("Entering createRestaurantOwner with email: {}", ownerDto.getEmail());
    String trimmedEmail = ownerDto.getEmail().trim().toLowerCase();
    if (ownerRepository.existsByEmail(trimmedEmail)) {
      log.warn("Email already exists: {}", ownerDto.getEmail());
      throw new ResourceAlreadyExistException(Constants.EMAIL_ALREADY_EXISTS);
    }

    ownerDto.setPassword(PasswordUtil.encode(ownerDto.getPassword()));
    RestaurantOwner owner = restaurantMapper.toEntity(ownerDto);
    ownerRepository.save(owner);

    log.info("Exiting createRestaurantOwner with email: {}", ownerDto.getEmail());
    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_OWNER_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
  }

  /**
   * Updates an existing restaurant owner's details.
   * Throws an exception if the owner is not found.
   *
   * @param id       the ID of the restaurant owner to be updated
   * @param ownerDto the DTO containing updated restaurant owner information
   * @return ResponseEntity containing a message DTO and HTTP status code
   */
  public ResponseEntity<MessageDTO> updateRestaurantOwner(Long id, RestaurantOwnerUpdateDTO ownerDto) {
    log.info("Entering updateRestaurantOwner with id: {} and email: {}", id, ownerDto.getEmail());

    Optional<RestaurantOwner> existingOwner = ownerRepository.findById(id);
    if (existingOwner.isEmpty()) {
      log.warn("Restaurant owner not found with id: {}", id);
      throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
    }

    RestaurantOwner owner = existingOwner.get();
    owner.setFirstName(ownerDto.getFirstName());
    owner.setLastName(ownerDto.getLastName());
    owner.setEmail(ownerDto.getEmail());
    owner.setContactNumber(ownerDto.getContactNumber());
//    owner.setPassword(PasswordUtil.encode(ownerDto.getPassword()));
    ownerRepository.save(owner);

    log.info("Exiting updateRestaurantOwner with id: {}", id);
    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_OWNER_UPDATED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Deletes a restaurant owner by their ID.
   * Returns a not found status if the owner does not exist.
   *
   * @param id the ID of the restaurant owner to be deleted
   * @return ResponseEntity containing a message DTO and HTTP status code
   */
  public ResponseEntity<MessageDTO> deleteRestaurantOwner(Long id) {
    log.info("Entering deleteRestaurantOwner with id: {}", id);

    if (!ownerRepository.existsById(id)) {
      log.warn("Restaurant owner not found with id: {}", id);
      return new ResponseEntity<>(new MessageDTO(Constants.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    ownerRepository.deleteById(id);
    log.info("Exiting deleteRestaurantOwner with id: {}", id);
    return new ResponseEntity<>(new MessageDTO(Constants.RESTAURANT_OWNER_DELETED_SUCCESSFULLY), HttpStatus.OK);
  }

  /**
   * Retrieves a restaurant owner by their ID.
   * Returns a not found status if the owner does not exist.
   *
   * @param id the ID of the restaurant owner to be retrieved
   * @return ResponseEntity containing the restaurant owner DTO and HTTP status code
   */
  public ResponseEntity<RestaurantOwnerOutDTO> getRestaurantOwner(Long id) {
    log.info("Entering getRestaurantOwner with id: {}", id);

    Optional<RestaurantOwner> owner = ownerRepository.findById(id);
    if (owner.isEmpty()) {
      log.warn("Restaurant owner not found with id: {}", id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    RestaurantOwnerOutDTO ownerDto = restaurantMapper.toDto(owner.get());
    log.info("Exiting getRestaurantOwner with id: {}", id);
    return new ResponseEntity<>(ownerDto, HttpStatus.OK);
  }

  /**
   * Retrieves all restaurant owners.
   *
   * @return ResponseEntity containing a list of restaurant owner DTOs and HTTP status code
   */
  public ResponseEntity<List<RestaurantOwnerOutDTO>> getAllRestaurantOwners() {
    log.info("Entering getAllRestaurantOwners");

    List<RestaurantOwner> owners = ownerRepository.findAll();
    List<RestaurantOwnerOutDTO> ownerDtos = owners.stream()
      .map(restaurantMapper::toDto)
      .toList();

    log.info("Exiting getAllRestaurantOwners");
    return new ResponseEntity<>(ownerDtos, HttpStatus.OK);
  }

  /**
   * Logs in a restaurant owner.
   * Checks the email and password, and returns owner details if login is successful.
   * Throws exceptions for incorrect credentials or non-existent email.
   *
   * @param loginDto the DTO containing email and password for login
   * @return ResponseEntity containing the restaurant owner DTO and HTTP status code
   */
  public ResponseEntity<RestaurantOwnerOutDTO> login(LoginDTO loginDto) {
    log.info("Entering login with email: {}", loginDto.getEmail());

    Optional<RestaurantOwner> ownerOpt = ownerRepository.findByEmail(loginDto.getEmail());
    if (ownerOpt.isEmpty()) {
      log.warn("Login failed, email not found: {}", loginDto.getEmail());
      throw new ResourceNotFoundException(Constants.EMAIL_NOT_FOUND);
    }

    RestaurantOwner owner = ownerOpt.get();
    String decodedPassword = PasswordUtil.decode(owner.getPassword());
    if (!decodedPassword.equals(loginDto.getPassword())) {
      log.warn("Login failed, password mismatch for email: {}", loginDto.getEmail());
      throw new ResourceNotFoundException(Constants.INCORRECT_PASSWORD);
    }

    log.info("Login successful for email: {}", loginDto.getEmail());

    RestaurantOwnerOutDTO ownerOutDTO = new RestaurantOwnerOutDTO();
    ownerOutDTO.setId(owner.getId());
    ownerOutDTO.setFirstName(owner.getFirstName());
    ownerOutDTO.setLastName(owner.getLastName());
    ownerOutDTO.setEmail(owner.getEmail());
    ownerOutDTO.setContactNumber(owner.getContactNumber());

    return new ResponseEntity<>(ownerOutDTO, HttpStatus.OK);
  }

  /**
   * Retrieves all restaurants associated with a specific owner.
   * Throws an exception if no restaurants are found for the given owner ID.
   *
   * @param ownerId the ID of the restaurant owner whose restaurants are to be retrieved
   * @return ResponseEntity containing a list of restaurant DTOs and HTTP status code
   */
  @Transactional
  public ResponseEntity<List<RestaurantOutDTO>> getRestaurantsByOwnerId(Long ownerId) {
    log.info("Entering getRestaurantsByOwnerId with ownerId: {}", ownerId);

    List<Restaurant> restaurants = restaurantRepository.findByRestaurantOwnerId(ownerId);
    if (restaurants.isEmpty()) {
      log.warn("No restaurants found for ownerId: {}", ownerId);
      throw new ResourceNotFoundException(Constants.NO_RESTAURANTS_FOUND_FOR_OWNER);
    }
    List<RestaurantOutDTO> restaurantOutDTOs = restaurants.stream()
      .map(this::toOutDTO)
      .collect(Collectors.toList());

    log.info("Exiting getRestaurantsByOwnerId with ownerId: {}", ownerId);
    return new ResponseEntity<>(restaurantOutDTOs, HttpStatus.OK);
  }

  /**
   * Handles forgot password requests by sending an email with the current password.
   * Throws an exception if the email is not found.
   *
   * @param forgotPasswordDTO the DTO containing the email for which the password needs to be sent
   */
  public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
    log.info("Processing forgot password for email: {}", forgotPasswordDTO.getEmail());
    RestaurantOwner owner = ownerRepository.findByEmail(forgotPasswordDTO.getEmail())
      .orElseThrow(() -> {
        log.warn("Email not found");
        return new ResourceNotFoundException(Constants.EMAIL_NOT_FOUND);
      });

    String decryptedPassword = PasswordUtil.decode(owner.getPassword());

    sendForgotPasswordEmail(owner.getEmail(), decryptedPassword);
    log.info("Forgot password email sent successfully");
  }

  /**
   * Sends a forgot password email to the specified address.
   *
   * @param email    the recipient's email address
   * @param password the password to be sent
   */
  private void sendForgotPasswordEmail(String email, String password) {
    log.info("Sending forgot password email to: {}", email);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("Forgot Password");
    message.setText("Your password is: " + password);

    javaMailSender.send(message);
  }

  /**
   * Converts a Restaurant entity to a RestaurantOutDTO.
   *
   * @param restaurant the Restaurant entity to be converted
   * @return the corresponding RestaurantOutDTO
   */
  RestaurantOutDTO toOutDTO(Restaurant restaurant) {
    RestaurantOutDTO dto = new RestaurantOutDTO();
    dto.setId(restaurant.getId());
    dto.setEmail(restaurant.getEmail());
    dto.setContactNumber(restaurant.getContactNumber());
    dto.setName(restaurant.getName());
    dto.setAddress(restaurant.getAddress());
    dto.setTimings(restaurant.getTimings());
    dto.setIsOpen(restaurant.getIsOpen());
    dto.setImage(restaurant.getImage());
    return dto;
  }
}
