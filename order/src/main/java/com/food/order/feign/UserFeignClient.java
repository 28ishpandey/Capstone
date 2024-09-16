package com.food.order.feign;

import com.food.order.dto.UserResponseDTO;
import com.food.order.feignfallback.UserFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for interacting with the user service.
 * It provides methods for retrieving and updating user details.
 */
@FeignClient(name = "user-service", url = "http://localhost:8080/users", fallback = UserFeignClientFallBack.class)
public interface UserFeignClient {

  /**
   * Retrieves a user by their ID.
   *
   * @param userId the ID of the user to retrieve.
   * @return the {@link UserResponseDTO} containing user details.
   */
  @GetMapping("/{userId}")
  UserResponseDTO getUserById(@PathVariable("userId") Long userId);

  /**
   * Updates the wallet balance for the specified user.
   *
   * @param userId the ID of the user whose balance will be updated.
   * @param newBalance the new balance to be set for the user's wallet.
   * @return the updated {@link UserResponseDTO} wrapped in a {@link ResponseEntity}.
   */
  @PutMapping("/{userId}/wallet-balance")
  ResponseEntity<UserResponseDTO> updateWalletBalance(@PathVariable("userId") Long userId, @RequestBody Double newBalance);
}


