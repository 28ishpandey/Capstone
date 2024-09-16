package com.food.order.feignfallback;

import com.food.order.dto.RestaurantOutDTO;
import com.food.order.exception.ResourceNotFoundException;
import com.food.order.feign.RestaurantFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for the {@link RestaurantFeignClient}.
 * This class provides default behavior when the restaurant service is unavailable.
 */
@Slf4j
@Component
public class RestaurantFeignClientFallBack implements RestaurantFeignClient {

  /**
   * Fallback method for retrieving a restaurant by its ID.
   *
   * @param id the ID of the restaurant.
   * @return nothing, as it throws a {@link ResourceNotFoundException}.
   * @throws ResourceNotFoundException when the restaurant service is unavailable.
   */
  @Override
  public RestaurantOutDTO getRestaurantById(Long id) {
    log.error("Fallback: Unable to get restaurant with ID: {}", id);
    throw new ResourceNotFoundException("Restaurant service is unavailable");
  }
}