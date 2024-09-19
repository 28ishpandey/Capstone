package com.food.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * The entry point for the Restaurant application.
 * <p>
 * This class is annotated with {@link SpringBootApplication} to mark it as the primary
 * Spring Boot application class. It contains the {@code main} method that starts the
 * application by calling {@link SpringApplication#run(Class, String...)}.
 * </p>
 */
@SpringBootApplication
public class RestaurantApplication {

  /**
   * The main method that serves as the entry point for the application.
   * <p>
   * This method initializes the Spring Boot application by calling the {@link SpringApplication#run(Class, String...)}
   * method with the {@code RestaurantApplication} class and any command-line arguments.
   * </p>
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(final String[] args) {
    SpringApplication.run(RestaurantApplication.class, args);
  }
}
