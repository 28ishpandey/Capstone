package com.food.order.feign;

import com.food.order.dto.RestaurantOutDTO;
import com.food.order.feignfallback.RestaurantFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with the restaurant service.
 * It provides methods for retrieving restaurant details.
 */
@FeignClient(name = "restaurant-service", url = "http://localhost:8081/restaurants",
  fallback = RestaurantFeignClientFallBack.class)
public interface RestaurantFeignClient {

  /**
   * Retrieves a restaurant by its ID.
   *
   * @param id the ID of the restaurant to retrieve.
   * @return the {@link RestaurantOutDTO} containing restaurant details.
   */
  @GetMapping("/{id}")
  RestaurantOutDTO getRestaurantById(@PathVariable("id") Long id);
}
