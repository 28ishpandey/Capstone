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

import java.util.List;
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

class UsersServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JavaMailSender javaMailSender;

  @InjectMocks
  private UsersService usersService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateUser_Success() {
    UserCreateDTO userCreateDTO =
      new UserCreateDTO("john@example.com", "9876543210", "John", "Doe", "123 Street", "password123", 1000.0);
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any(Users.class))).thenReturn(new Users());

    UserResponseDTO responseDTO = usersService.createUser(userCreateDTO);

    assertNotNull(responseDTO);
    verify(userRepository, times(1)).save(any(Users.class));
  }

  @Test
  void testCreateUser_AccountExists() {

    UserCreateDTO userCreateDTO =
      new UserCreateDTO("john@example.com", "9876543210", "John", "Doe", "123 Street", "password123", 1000.0);
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new Users()));

    assertThrows(AccountExistException.class, () -> usersService.createUser(userCreateDTO));
    verify(userRepository, never()).save(any(Users.class));
  }

  @Test
  void testGetUserById_Success() {

    Users user = new Users();
    user.setUserId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    UserResponseDTO responseDTO = usersService.getUserById(1L);

    assertNotNull(responseDTO);
    assertEquals(1L, responseDTO.getUserId());
  }

  @Test
  void testGetUserById_NotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.getUserById(1L));
  }

  @Test
  void testGetAllUsers() {
    when(userRepository.findAll()).thenReturn(List.of(new Users(), new Users()));

    List<UserResponseDTO> users = usersService.getAllUsers();

    assertEquals(2, users.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testUpdateUser_Success() {

    Users existingUser = new Users();
    existingUser.setUserId(1L);
    existingUser.setEmail("john@example.com");
    UserUpdateDTO userUpdateDTO = new UserUpdateDTO("john@example.com", "9876543210", "John", "Doe", "456 Street", 1500.0);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(Users.class))).thenReturn(existingUser);

    UserResponseDTO responseDTO = usersService.updateUser(1L, userUpdateDTO);

    assertNotNull(responseDTO);
    assertEquals("456 Street", responseDTO.getAddress());
  }

  @Test
  void testUpdateUser_EmailExists() {

    Users existingUser = new Users();
    existingUser.setUserId(1L);
    existingUser.setEmail("john@example.com");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new Users()));

    UserUpdateDTO userUpdateDTO = new UserUpdateDTO("newemail@example.com", "9876543210", "John", "Doe", "456 Street", 1500.0);

    assertThrows(AccountExistException.class, () -> usersService.updateUser(1L, userUpdateDTO));
  }

  @Test
  void testDeleteUser_Success() {

    Users user = new Users();
    user.setUserId(1L);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    usersService.deleteUser(1L);

    verify(userRepository, times(1)).delete(user);
  }

  @Test
  void testDeleteUser_NotFound() {

    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.deleteUser(1L));
  }

  @Test
  void testLogin_Success() {

    LoginDTO loginDTO = new LoginDTO("john@example.com", "password123");
    Users user = new Users();
    user.setEmail("john@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    UserResponseDTO responseDTO = usersService.login(loginDTO);

    assertNotNull(responseDTO);
    assertEquals("john@example.com", responseDTO.getEmail());
  }

  @Test
  void testLogin_InvalidCredentials() {

    LoginDTO loginDTO = new LoginDTO("john@example.com", "wrongpassword");
    Users user = new Users();
    user.setEmail("john@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    assertThrows(InvalidCredentials.class, () -> usersService.login(loginDTO));
  }

  @Test
  void testForgotPassword_Success() {

    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO("john@example.com");
    Users user = new Users();
    user.setEmail("john@example.com");
    user.setPassword(PasswordUtil.encryptPassword("password123"));
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

    usersService.forgotPassword(forgotPasswordDTO);

    verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
  }

  @Test
  void testForgotPassword_NotFound() {

    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO("john@example.com");
    when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.forgotPassword(forgotPasswordDTO));
  }

  @Test
  void testChangePassword_Success() {

    Users user = new Users();
    user.setUserId(1L);
    user.setPassword(PasswordUtil.encryptPassword("oldpassword"));
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.changePassword(1L, "newpassword");

    assertNotNull(responseDTO);
  }

  @Test
  void testChangePassword_NotFound() {

    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> usersService.changePassword(1L, "newpassword"));
  }

  @Test
  void testUpdateAddress_Success() {
    Users user = new Users();
    user.setUserId(1L);
    user.setAddress("Old Address");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.updateAddress(1L, "New Address");

    assertEquals("New Address", responseDTO.getAddress());
  }

  @Test
  void testUpdateWalletBalance_Success() {
    Users user = new Users();
    user.setUserId(1L);
    user.setWalletBalance(500.0);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(Users.class))).thenReturn(user);

    UserResponseDTO responseDTO = usersService.updateWalletBalance(1L, 1000.0);

    assertEquals(1000.0, responseDTO.getWalletBalance());
  }
}
