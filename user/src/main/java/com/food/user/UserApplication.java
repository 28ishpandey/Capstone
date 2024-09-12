package com.food.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The main entry point for the Spring Boot application.
 * <p>
 * This class contains the main method that launches the Spring Boot application.
 * </p>
 */
@SpringBootApplication
public class UserApplication {

  /**
   * The main method which starts the Spring Boot application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}