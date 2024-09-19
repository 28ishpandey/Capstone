package com.food.restaurant.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up Cross-Origin Resource Sharing (CORS) settings.
 * <p>
 * This configuration allows requests from any origin and supports various HTTP methods.
 * It is used to configure CORS mappings for the entire application.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS mappings for the application.
   * <p>
   * This method allows requests from any origin ("*"), supports all standard HTTP methods
   * including GET, POST, PUT, DELETE, and OPTIONS, and allows all headers.
   * </p>
   *
   * @param registry the {@link CorsRegistry} to configure
   */
  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*");
  }
}

