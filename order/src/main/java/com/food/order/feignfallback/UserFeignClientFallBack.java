package com.food.order.feignfallback;

import com.food.order.dto.UserResponseDTO;
import com.food.order.exception.ResourceNotFoundException;
import com.food.order.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
/**
 * Fallback implementation for the {@link UserFeignClient}.
 * This class provides default behavior when the user service is unavailable.
 */
@Slf4j
@Component
public class UserFeignClientFallBack implements UserFeignClient {

  /**
   * Fallback method for retrieving a user by their ID.
   *
   * @param userId the ID of the user.
   * @return nothing, as it throws a {@link ResourceNotFoundException}.
   * @throws ResourceNotFoundException when the user service is unavailable.
   */
  @Override
  public UserResponseDTO getUserById(Long userId) {
    log.error("Fallback: Unable to get user with ID: {}", userId);
    throw new ResourceNotFoundException("User service is unavailable");
  }

  /**
   * Fallback method for updating the wallet balance for a user.
   *
   * @param userId the ID of the user.
   * @param newBalance the new wallet balance to be updated.
   * @return nothing, as it throws a {@link ResourceNotFoundException}.
   * @throws ResourceNotFoundException when the user service is unavailable.
   */
  @Override
  public ResponseEntity<UserResponseDTO> updateWalletBalance(Long userId, Double newBalance) {
    log.error("Fallback: Unable to update wallet balance for user with ID: {}", userId);
    throw new ResourceNotFoundException("User service is unavailable");
  }
}
