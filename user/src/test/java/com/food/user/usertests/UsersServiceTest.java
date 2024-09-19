package com.food.user.usertests;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.dto.UserUpdateDTO;
import com.food.user.entity.Users;
import com.food.user.exception.AccountExistException;
import com.food.user.exception.AccountNotFoundException;
import com.food.user.exception.InvalidCredentials;
import com.food.user.repository.UserRepository;
import com.food.user.service.UsersService;
import com.food.user.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for UsersService.
 */
class UsersServiceTest {

  /**
   * Default wallet balance for new users.
   */
  private static final double DEFAULT_WALLET_BALANCE = 1000.0;

  /**
   * Updated wallet balance for testing.
   */
  private static final double UPDATED_WALLET_BALANCE = 1500.0;

  /**
   * Mock of UserRepository for testing.
   */
  @Mock
  private UserRepository userRepository;

  /**
   * Mock of JavaMailSender for testing.
   */
  @Mock
  private JavaMailSender javaMailSender;

  /**
   * UsersService instance for testing.
   */
  @InjectMocks
  private UsersService usersService;

  /**
   * Set up test environment before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test successful user creation.
   */
  @Test
  void testCreateUserSuccess() {
    UserCreateDTO userCreateDTO =
      new UserCreateDTO("test@example.com", "9876543210", "password", "firstname", "lastname", "address", DEFAULT_WALLET_BALANCE);
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any(Users.class))).thenReturn(new Users());

    UserResponseDTO responseDTO = usersService.createUser(userCreateDTO);

    assertNotNull(responseDTO);
    verify(userRepository, times(1)).save(any(Users.class));
  }

  /**
   * Test user creation when account already exists.
   */
  @Test
  void testCreateUserAccountExists() {
    UserCreateDTO userCreateDTO =
      new UserCreateDTO("email@example.com", "9876543210", "password", "firstname", "lastname",
        "address", DEFAULT_WALLET_BALANCE);
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new Users()));

    assertThrows(AccountExistException.class, () -> usersService.createUser(userCreateDTO));
    verify(userRepository, never()).save(any(Users.class));
  }

  /**
   * Test successful retrieval of user by ID.
   */
  @Test
  void testGetUserByIdSuccess() {
    Users user = new Users();
    user.setUserId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    UserResponseDTO responseDTO = usersService.getUserById(1L);

    assertNotNull(responseDTO);
    assertEquals(1L, responseDTO.getUserId());
  }

  /**
   * Test retrieval of non-existent user by ID.
   */
  @Test
  void testGetUserByIdNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.getUserById(1L));
  }

  /**
   * Test successful user update.
   */
  @Test
  void testUpdateUserSuccess() {
    Users existingUser = new Users();
    existingUser.setUserId(1L);
    existingUser.setEmail("newemail@example.com");
    UserUpdateDTO userUpdateDTO = new UserUpdateDTO("newemail@example.com", "9876543210",
      "firstname", "lastname", "address", UPDATED_WALLET_BALANCE);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(Users.class))).thenReturn(existingUser);

    UserResponseDTO responseDTO = usersService.updateUser(1L, userUpdateDTO);

    assertNotNull(responseDTO);
    assertEquals("address", responseDTO.getAddress());
  }

  /**
   * Test user update when email already exists.
   */
  @Test
  void testUpdateUserEmailExists() {
    Users existingUser = new Users();
    existingUser.setUserId(1L);
    existingUser.setEmail("email@example.com");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new Users()));

    UserUpdateDTO userUpdateDTO = new UserUpdateDTO("email1@example.com", "9876543210",
      "firstname", "lastname", "address", UPDATED_WALLET_BALANCE);

    assertThrows(AccountExistException.class, () -> usersService.updateUser(1L, userUpdateDTO));
  }

  /**
   * Test successful user deletion.
   */
  @Test
  void testDeleteUserSuccess() {
    Users user = new Users();
    user.setUserId(1L);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    usersService.deleteUser(1L);

    verify(userRepository, times(1)).delete(user);
  }

  /**
   * Test deletion of non-existent user.
   */
  @Test
  void testDeleteUserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.deleteUser(1L));
  }

  /**
   * Test successful user login.
   */
  @Test
  void testLoginSuccess() {
    LoginDTO loginDTO = new LoginDTO("email@example.com", "password123");
    Users user = new Users();
    user.setEmail("email@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    UserResponseDTO responseDTO = usersService.login(loginDTO);

    assertNotNull(responseDTO);
    assertEquals("email@example.com", responseDTO.getEmail());
  }

  /**
   * Test login with invalid credentials.
   */
  @Test
  void testLoginInvalidCredentials() {
    LoginDTO loginDTO = new LoginDTO("email@example.com", "wrongpassword");
    Users user = new Users();
    user.setEmail("email@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    assertThrows(InvalidCredentials.class, () -> usersService.login(loginDTO));
  }

  /**
   * Test successful password reset request.
   */
  @Test
  void testForgotPasswordSuccess() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO("email@example.com");
    Users user = new Users();
    user.setEmail("email@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    usersService.forgotPassword(forgotPasswordDTO);

    verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
  }

  /**
   * Test password reset request for non-existent user.
   */
  @Test
  void testForgotPasswordNotFound() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO("email@example.com");
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.forgotPassword(forgotPasswordDTO));
  }

  /**
   * Test successful password change.
   */
  @Test
  void testChangePasswordSuccess() {
    Users user = new Users();
    user.setUserId(1L);
    user.setPassword(PasswordUtil.encryptPassword("oldpassword"));
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.changePassword(1L, "newpassword");

    assertNotNull(responseDTO);
  }

  /**
   * Test password change for non-existent user.
   */
  @Test
  void testChangePasswordNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.changePassword(1L, "newpassword"));
  }

  /**
   * Test successful address update.
   */
  @Test
  void testUpdateAddressSuccess() {
    Users user = new Users();
    user.setUserId(1L);
    user.setAddress("Old Address");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.updateAddress(1L, "New Address");

    assertEquals("New Address", responseDTO.getAddress());
  }

  /**
   * Test successful wallet balance update.
   */
  @Test
  void testUpdateWalletBalanceSuccess() {
    Users user = new Users();
    user.setUserId(1L);
    user.setWalletBalance(DEFAULT_WALLET_BALANCE / 2);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.updateWalletBalance(1L, DEFAULT_WALLET_BALANCE);

    assertEquals(DEFAULT_WALLET_BALANCE, responseDTO.getWalletBalance());
  }
}
