package com.food.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Order microservice application.
 * This class is responsible for bootstrapping the Spring Boot application
 * and enabling Feign clients for communication with other services.
 *
 * <p>The application uses Feign clients to interact with external services, and
 * Feign clients are scanned within the package "com.food.order.feign".
 *
 * <p>Annotations used:
 * <ul>
 *   <li>{@link SpringBootApplication}: Indicates that this is a Spring Boot application,
 *   which provides the main configuration and auto-configuration features.
 *   <li>{@link EnableFeignClients}: Enables Feign clients in the specified package to allow
 *   communication with external services.
 * </ul>
 *
 * <p>Usage:
 * To run the Order microservice, execute the main method, which will
 * initialize the Spring Boot application context and start the application.
 *
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.food.order.feign")
public class OrderApplication {

  /**
   * The main method serves as the entry point for the Order microservice.
   * It launches the Spring Boot application using {@link SpringApplication#run(Class, String[])}.
   *
   * @param args Command-line arguments passed during the application startup.
   */
  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }

}
