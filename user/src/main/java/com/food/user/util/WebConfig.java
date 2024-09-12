package com.food.user.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings, including CORS mappings.
 * <p>
 * This class implements {@link WebMvcConfigurer} to customize the CORS configuration
 * for the application.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS (Cross-Origin Resource Sharing) settings.
   * <p>
   * Allows all origins, methods, and headers for all endpoints.
   * </p>
   *
   * @param registry the {@link CorsRegistry} to modify
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*");
  }
}
