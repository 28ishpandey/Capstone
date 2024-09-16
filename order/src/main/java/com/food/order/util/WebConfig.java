package com.food.order.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Configuration class for setting up CORS mappings.
 * Allows cross-origin requests to be made to the API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS mappings to allow requests from any origin and standard HTTP methods.
   *
   * @param registry the {@link CorsRegistry} used to register CORS mappings.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*");
  }
}