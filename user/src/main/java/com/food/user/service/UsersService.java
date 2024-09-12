package com.food.user.service;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.dto.UserUpdateDTO;
import com.food.user.entity.Users;
import com.food.user.exception.AccountExistException;
import com.food.user.exception.AccountNotFoundException;
import com.food.user.exception.InvalidCredentials;
import com.food.user.repository.UserRepository;
import com.food.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user operations such as creation, retrieval, update, deletion, and authentication.
 */
@Slf4j
@Service
public class UsersService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JavaMailSender javaMailSender;

  /**
   * Creates a new user in the system.
   *
   * @param userCreateDTO the user details for creation
   * @return the created user details
   */

  public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
    log.info("Creating user with email: {}", userCreateDTO.getEmail());
    String trimmedEmail = userCreateDTO.getEmail().trim().toLowerCase();
    Optional<Users> existingUser = userRepository.findByEmailIgnoreCase(trimmedEmail);
    if (existingUser.isEmpty()) {
      Users user = new Users();
      user.setEmail(trimmedEmail);
      user.setContactNumber(userCreateDTO.getContactNumber());
      String encryptedPassword = PasswordUtil.encryptPassword(userCreateDTO.getPassword());
      user.setPassword(encryptedPassword);
      user.setFirstName(userCreateDTO.getFirstName());
      user.setLastName(userCreateDTO.getLastName());
      user.setAddress(userCreateDTO.getAddress());
      user.setWalletBalance(1000.0);

      Users savedUser = userRepository.save(user);
      log.info("User created with ID: {}", savedUser.getUserId());
      return convertToResponseDTO(savedUser);
    }
    throw new AccountExistException();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param userId the ID of the user
   * @return the user details
   */
  public UserResponseDTO getUserById(Long userId) {
    log.info("Fetching user with ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);
    return convertToResponseDTO(user);
  }

  /**
   * Retrieves all users in the system.
   *
   * @return a list of user details
   */
  public List<UserResponseDTO> getAllUsers() {
    log.info("Fetching all users");
    return userRepository.findAll().stream()
      .map(this::convertToResponseDTO)
      .collect(Collectors.toList());
  }

  /**
   * Updates a user's details.
   *
   * @param userId        the ID of the user to update
   * @param userUpdateDTO the updated user details
   * @return the updated user details
   */

  public UserResponseDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
    log.info("Updating user with ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);

    // Trim and convert email to lowercase
    String updatedEmail = userUpdateDTO.getEmail().trim().toLowerCase();

    // Check if the new email is different from the current one
    if (!updatedEmail.equals(user.getEmail())) {
      // Check if the new email is already in use by another user
      if (userRepository.findByEmailIgnoreCase(updatedEmail).isPresent()) {
        log.warn("Email {} is already in use by another user", updatedEmail);
        throw new AccountExistException();
      }
      user.setEmail(updatedEmail);
    }

    user.setContactNumber(userUpdateDTO.getContactNumber());
    user.setFirstName(userUpdateDTO.getFirstName());
    user.setLastName(userUpdateDTO.getLastName());
    user.setAddress(userUpdateDTO.getAddress());
    user.setWalletBalance(userUpdateDTO.getWalletBalance());

    Users savedUser = userRepository.save(user);
    log.info("User updated with ID: {}", savedUser.getUserId());
    return convertToResponseDTO(savedUser);
  }

  /**
   * Deletes a user by their ID.
   *
   * @param userId the ID of the user to delete
   */
  public void deleteUser(Long userId) {
    log.info("Deleting user with ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);
    userRepository.delete(user);
    log.info("User deleted with ID: {}", userId);
  }

  /**
   * Authenticates a user.
   *
   * @param loginDTO the login credentials
   * @return the user details if authentication is successful
   */
  public UserResponseDTO login(LoginDTO loginDTO) {
    log.info("Attempting login for email: {}", loginDTO.getEmail());
    Users user = userRepository.findByEmailIgnoreCase(loginDTO.getEmail())
      .orElseThrow(AccountNotFoundException::new);

    String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

    if (!decryptedPassword.equals(loginDTO.getPassword())) {
      throw new InvalidCredentials();
    }

    log.info("Login successful for user ID: {}", user.getUserId());
    return convertToResponseDTO(user);
  }

  /**
   * Handles forgot password requests by sending a reset email.
   *
   * @param forgotPasswordDTO the details for forgot password
   */
  public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
    log.info("Processing forgot password for email: {}", forgotPasswordDTO.getEmail());
    Users user = userRepository.findByEmailIgnoreCase(forgotPasswordDTO.getEmail())
      .orElseThrow(AccountNotFoundException::new);

    String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

    sendForgotPasswordEmail(user.getEmail(), decryptedPassword);
    log.info("Forgot password email sent successfully");
  }

  /**
   * Sends a reset password email to the user.
   *
   * @param email the email address to send the reset email to
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
   * Changes the user's password.
   *
   * @param userId      the ID of the user
   * @param newPassword the new password
   * @return the updated user details
   */

  public UserResponseDTO changePassword(Long userId, String newPassword) {
    log.info("Changing password for user ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);

    String encryptedPassword = PasswordUtil.encryptPassword(newPassword);
    user.setPassword(encryptedPassword);

    Users updatedUser = userRepository.save(user);
    log.info("Password changed successfully for user ID: {}", userId);
    return convertToResponseDTO(updatedUser);
  }

  /**
   * Updates the address for a specified user.
   * <p>
   * This method retrieves the user by their ID, updates their address with the new value,
   * saves the updated user, and returns a {@link UserResponseDTO} representing the updated user.
   * </p>
   *
   * @param userId the ID of the user whose address is to be updated
   * @param newAddress the new address to set for the user
   * @return a {@link UserResponseDTO} containing the updated user information
   * @throws AccountNotFoundException if the user with the specified ID is not found
   */
  public UserResponseDTO updateAddress(Long userId, String newAddress) {
    log.info("Updating address for user ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);
    user.setAddress(newAddress);
    Users updatedUser = userRepository.save(user);
    log.info("Address updated successfully for user ID: {}", userId);
    return convertToResponseDTO(updatedUser);
  }

  /**
   * Updates the wallet balance for a specified user.
   * <p>
   * This method retrieves the user by their ID, updates their wallet balance with the new value,
   * saves the updated user, and returns a {@link UserResponseDTO} representing the updated user.
   * </p>
   *
   * @param userId the ID of the user whose wallet balance is to be updated
   * @param newBalance the new wallet balance to set for the user
   * @return a {@link UserResponseDTO} containing the updated user information
   * @throws AccountNotFoundException if the user with the specified ID is not found
   */
  public UserResponseDTO updateWalletBalance(Long userId, Double newBalance) {
    log.info("Updating wallet balance for user ID: {}", userId);
    Users user = userRepository.findById(userId)
      .orElseThrow(AccountNotFoundException::new);
    user.setWalletBalance(newBalance);
    Users updatedUser = userRepository.save(user);
    log.info("Wallet balance updated successfully for user ID: {}", userId);
    return convertToResponseDTO(updatedUser);
  }

  /**
   * Converts a {@link Users} entity to a {@link UserResponseDTO}.
   * <p>
   * This method creates a new {@link UserResponseDTO} and populates it with the user's details
   * such as user ID, email, contact number, first name, last name, address, and wallet balance.
   * </p>
   *
   * @param user the {@link Users} entity to convert
   * @return a {@link UserResponseDTO} containing the user's details
   */
  private UserResponseDTO convertToResponseDTO(Users user) {
    UserResponseDTO responseDTO = new UserResponseDTO();
    responseDTO.setUserId(user.getUserId());
    responseDTO.setEmail(user.getEmail());
    responseDTO.setContactNumber(user.getContactNumber());
    responseDTO.setFirstName(user.getFirstName());
    responseDTO.setLastName(user.getLastName());
    responseDTO.setAddress(user.getAddress());
    responseDTO.setWalletBalance(user.getWalletBalance());
    return responseDTO;
  }
}