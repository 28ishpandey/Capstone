package com.food.restaurant.util;

import java.util.Base64;

/**
 * Utility class for encoding and decoding passwords using Base64 encoding.
 * <p>
 * This class provides static methods for encoding and decoding passwords to
 * facilitate secure storage and retrieval. The encoding and decoding process
 * uses Base64 encoding, which is suitable for scenarios where passwords need
 * to be encoded for transport or storage.
 * </p>
 */
public class PasswordUtil {

  /**
   * Encodes the given password using Base64 encoding.
   * <p>
   * This method converts the password string to a byte array and then encodes
   * it to a Base64 encoded string. This encoded string can be used for storage
   * or transmission where encoding is required.
   * </p>
   *
   * @param password the password to be encoded
   * @return the Base64 encoded string representation of the password
   */
  public static String encode(String password) {
    return Base64.getEncoder().encodeToString(password.getBytes());
  }

  /**
   * Decodes the given Base64 encoded password.
   * <p>
   * This method decodes a Base64 encoded string back to its original password
   * representation. The decoded string can be used for validation or processing
   * of the password.
   * </p>
   *
   * @param encodedPassword the Base64 encoded string to be decoded
   * @return the decoded password string
   */
  public static String decode(String encodedPassword) {
    return new String(Base64.getDecoder().decode(encodedPassword));
  }
}
